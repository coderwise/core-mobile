package com.coderwise.core.location

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Instant

class LocationServiceImpl(
    private val locationManager: LocationManager,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) : LocationService {
    private val _status = MutableStateFlow(LocationService.Status.STOPPED)
    override val status: Flow<LocationService.Status> = _status

    @SuppressLint("MissingPermission")
    override val coordinates: Flow<GpsMessage> = callbackFlow {
        val callback = LocationListener { location -> trySend(location.asGpsMessage()) }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            5000,
            1F,
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
                //permissionService.checkPermission(Permission.LOCATION)
            }
        }
    }.shareIn(
        scope = scope,
        started = SharingStarted.Companion.Lazily,
        replay = 1
    )

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