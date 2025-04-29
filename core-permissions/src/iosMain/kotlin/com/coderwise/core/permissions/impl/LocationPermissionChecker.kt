package com.coderwise.core.permissions.impl

import com.coderwise.core.permissions.LOCATION
import com.coderwise.core.permissions.Permission
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusRestricted
import platform.darwin.NSObject

class LocationPermissionChecker(
    private val onStatusUpdate: (Permission, Permission.Status) -> Unit
) : PermissionChecker {
    private val permissionDelegate = PermissionDelegate { status ->
        onStatusUpdate(Permission.LOCATION, status)
    }

    private val locationManager = CLLocationManager().apply {
        delegate = permissionDelegate
    }

    override fun check(): Permission.Status {
        val iosStatus = locationManager.authorizationStatus()
        val status = iosStatus.asLocationPermissionStatus()
        return status
    }

    override fun request() {
        locationManager.requestWhenInUseAuthorization()
    }

    private class PermissionDelegate(
        private val onStatusUpdate: (Permission.Status) -> Unit
    ) : NSObject(), CLLocationManagerDelegateProtocol {

        override fun locationManager(
            manager: CLLocationManager,
            didChangeAuthorizationStatus: CLAuthorizationStatus
        ) {
            onStatusUpdate(didChangeAuthorizationStatus.asLocationPermissionStatus())
        }
    }
}

private fun Int.asLocationPermissionStatus(): Permission.Status = when (this) {
    kCLAuthorizationStatusAuthorizedAlways,
    kCLAuthorizationStatusAuthorizedWhenInUse -> Permission.Status.GRANTED

    kCLAuthorizationStatusRestricted,
    kCLAuthorizationStatusDenied -> Permission.Status.DENIED

    else -> Permission.Status.PENDING
}