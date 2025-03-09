package com.coderwise.core.location.impl

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import com.coderwise.core.location.GpsMessage
import com.coderwise.core.location.LatLon
import com.coderwise.core.location.LocationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Instant

class AndroidLocationServiceImpl(
    private val locationManager: LocationManager,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) : LocationServiceImpl(scope) {

    override fun startUpdates(): Job = updatesFlow(configuration)
        .onEach {
            _messages.trySend(it)
        }
        .launchIn(scope)

    @SuppressLint("MissingPermission")
    private fun updatesFlow(configuration: LocationService.Configuration) = callbackFlow {
        val callback = LocationListener { location -> trySend(location.asGpsMessage()) }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            configuration.minTime.inWholeMilliseconds,
            configuration.minDistance,
            callback
        )
        _status.update { LocationService.Status.STARTED }

        awaitClose {
            locationManager.removeUpdates(callback)
            _status.value = LocationService.Status.STOPPED
        }
    }.retry { e ->
        (e is SecurityException).also {
            if (it) {
                _status.value = LocationService.Status.ERROR
                delay(RETRY_DELAY)
            }
        }
    }

    private fun Location.asGpsMessage() = GpsMessage(
        latLon = LatLon(latitude, longitude),
        horizontalAccuracy = accuracy,
        altitude = altitude,
        verticalAccuracy = verticalAccuracyMeters,
        speed = speed,
        speedAccuracyMps = speedAccuracyMetersPerSecond,
        bearing = bearing,
        bearingAccuracyDegrees = bearingAccuracyDegrees,
        timestamp = Instant.fromEpochMilliseconds(time),
        provider = provider
    )

    companion object {
        private const val RETRY_DELAY = 5000L
    }
}