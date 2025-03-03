package com.coderwise.permissions.impl

import com.coderwise.permissions.LOCATION
import com.coderwise.permissions.Permission
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusRestricted
import platform.darwin.NSObject

class IosPermissionService() : PermissionServiceImpl() {
    private val permissionDelegate = PermissionDelegate {
        // TODO route requested permission
        checkPermission(Permission.LOCATION)
    }

    private val locationManager = CLLocationManager().apply {
        delegate = permissionDelegate
    }

    override fun checkPermission(permission: Permission): Permission.Status {
        val iosStatus = locationManager.authorizationStatus()
        val status = iosStatus.asPermissionStatus()
        updateStatus(permission, status)
        return status
    }

    private class PermissionDelegate(
        private val onStatusUpdate: (Permission.Status) -> Unit
    ) : NSObject(), CLLocationManagerDelegateProtocol {

        override fun locationManager(
            manager: CLLocationManager,
            didChangeAuthorizationStatus: CLAuthorizationStatus
        ) {
            onStatusUpdate(didChangeAuthorizationStatus.asPermissionStatus())
        }
    }
}

fun Int.asPermissionStatus(): Permission.Status = when (this) {
    kCLAuthorizationStatusAuthorizedAlways,
    kCLAuthorizationStatusAuthorizedWhenInUse -> Permission.Status.GRANTED

    kCLAuthorizationStatusRestricted,
    kCLAuthorizationStatusDenied -> Permission.Status.NOT_GRANTED_PERMANENTLY

    else -> Permission.Status.NOT_GRANTED
}