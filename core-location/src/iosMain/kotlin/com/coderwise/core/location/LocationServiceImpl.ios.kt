package com.coderwise.core.location

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLDistanceFilterNone
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSError
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
class LocationServiceImpl : LocationService {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private val locationManager = CLLocationManager()

    private val _status = MutableStateFlow(LocationService.Status.STOPPED)

    override val status: Flow<LocationService.Status> = _status

    override val coordinates: Flow<LatLon> = callbackFlow {
        locationManager.requestWhenInUseAuthorization()
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.distanceFilter = kCLDistanceFilterNone
        locationManager.startUpdatingLocation()
        val locationDelegate = LocationDelegate()
        locationDelegate.onLocationUpdate = { location ->
            location?.let { trySend(location) }
        }

        locationManager.delegate = locationDelegate

        _status.update { LocationService.Status.STARTED }

        awaitClose {
            locationManager.stopUpdatingLocation()
            _status.value = LocationService.Status.STOPPED
        }
    }.shareIn(
        scope = scope,
        started = SharingStarted.Companion.Lazily,
        replay = 1
    )

    private class LocationDelegate : NSObject(), CLLocationManagerDelegateProtocol {
        // Define a callback to receive location updates
        var onLocationUpdate: ((LatLon?) -> Unit)? = null

        override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
            didUpdateLocations.lastOrNull()?.let {
                val location = it as CLLocation
                location.coordinate.useContents {
                    onLocationUpdate?.invoke(LatLon(latitude, longitude))
                }
            }
        }

        override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
            onLocationUpdate?.invoke(null)
        }
    }
}