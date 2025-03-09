package com.coderwise.core.location.impl

import com.coderwise.core.location.GpsMessage
import com.coderwise.core.location.LocationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

abstract class LocationServiceImpl(
    private val scope: CoroutineScope
) : LocationService {
    private var locationUpdatesJob: Job? = null

    private var configuration = LocationService.Configuration(1.seconds, 1f)

    protected val _status = MutableStateFlow(LocationService.Status.STOPPED)
    override val status: Flow<LocationService.Status> = _status

    private val _messages = Channel<GpsMessage>(BUFFERED)
    override val gpsMessages: Flow<GpsMessage> = _messages
        .receiveAsFlow()
        .shareIn(
            scope = scope,
            started = SharingStarted.Companion.Lazily,
            replay = 1
        )

    override suspend fun start() {
        _status.value = LocationService.Status.STARTING
        locationUpdatesJob = startUpdates()
    }

    override suspend fun stop() {
        _status.value = LocationService.Status.STOPPING
        locationUpdatesJob?.cancel()
        locationUpdatesJob = null
    }

    override suspend fun configure(minTime: Duration, minDistance: Float) {
        stop()
        configuration = LocationService.Configuration(minTime, minDistance)
        start()
    }

    private fun startUpdates(): Job = updatesFlow(configuration)
        .onEach {
            _messages.trySend(it)
        }
        .launchIn(scope)

    protected abstract fun updatesFlow(configuration: LocationService.Configuration): Flow<GpsMessage>
}