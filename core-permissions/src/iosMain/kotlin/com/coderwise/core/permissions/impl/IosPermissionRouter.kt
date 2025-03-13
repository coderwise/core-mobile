package com.coderwise.core.permissions.impl

import com.coderwise.core.permissions.CAMERA
import com.coderwise.core.permissions.LOCATION
import com.coderwise.core.permissions.Permission

class IosPermissionRouter(
    onStatusUpdated: (Permission, Permission.Status) -> Unit
) {
    private val locationPermissionDelegate = LocationPermissionDelegate(onStatusUpdated)
    private val cameraPermissionDelegate = CameraPermissionDelegate(onStatusUpdated)

    fun check(permission: Permission): Permission.Status {
        return when (permission) {
            Permission.LOCATION -> locationPermissionDelegate.check()
            Permission.CAMERA -> cameraPermissionDelegate.check()
            else -> error("Unsupported permission: $permission")
        }
    }

    fun request(permission: Permission) {
        when (permission) {
            Permission.LOCATION -> locationPermissionDelegate.request()
            Permission.CAMERA -> cameraPermissionDelegate.request()
            else -> error("Unsupported permission: $permission")
        }
    }
}