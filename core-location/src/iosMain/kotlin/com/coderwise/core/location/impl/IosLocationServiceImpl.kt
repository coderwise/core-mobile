package com.coderwise.core.location.impl

import com.coderwise.core.location.GpsMessage
import com.coderwise.core.location.LatLon
import com.coderwise.core.location.LocationService
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.datetime.toKotlinInstant
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLDistanceFilterNone
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSError
import platform.darwin.NSObject

class IosLocationServiceImpl : LocationServiceImpl() {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private val locationManager = CLLocationManager()

    override val gpsMessages: Flow<GpsMessage> = callbackFlow {
        locationManager.requestWhenInUseAuthorization()
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.distanceFilter = kCLDistanceFilterNone
        locationManager.startUpdatingLocation()
        val locationDelegate = LocationDelegate()
        locationDelegate.onLocationUpdate = { location ->
            location?.let { trySend(location.asGpsMessage()) }
        }

        locationManager.delegate = locationDelegate

        _status.update { LocationService.Status.STARTED }

        awaitClose {
            locationManager.stopUpdatingLocation()
            _status.value = LocationService.Status.STOPPED
        }
    }.shareIn(
        scope = scope,
        started = SharingStarted.Lazily,
        replay = 1
    )

    private class LocationDelegate : NSObject(), CLLocationManagerDelegateProtocol {
        // Define a callback to receive location updates
        var onLocationUpdate: ((CLLocation?) -> Unit)? = null

        override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
            didUpdateLocations.lastOrNull()?.let {
                val location = it as CLLocation
                onLocationUpdate?.invoke(location)
            }
        }

        override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
            onLocationUpdate?.invoke(null)
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun CLLocation.asGpsMessage() = coordinate.useContents {
    GpsMessage(
        latLon = LatLon(latitude, longitude),
        horizontalAccuracy = horizontalAccuracy.toFloat(),
        altitude = altitude,
        verticalAccuracy = verticalAccuracy.toFloat(),
        speed = speed.toFloat(),
        speedAccuracyMps = speedAccuracy.toFloat(),
        bearing = course.toFloat(),
        bearingAccuracyDegrees = courseAccuracy.toFloat(),
        timestamp = timestamp.toKotlinInstant(),
        provider = sourceInformation?.description
    )
}
