package com.coderwise.core.permissions.impl

import com.coderwise.core.permissions.CAMERA
import com.coderwise.core.permissions.LOCATION
import com.coderwise.core.permissions.NOTIFICATIONS
import com.coderwise.core.permissions.Permission

class IosPermissionRouter(
    onStatusUpdated: (Permission, Permission.Status) -> Unit
) {
    private val locationPermission = LocationPermissionChecker(onStatusUpdated)
    private val cameraPermission = CameraPermissionChecker(onStatusUpdated)
    private val notificationsPermission = NotificationsPermissionChecker(onStatusUpdated)

    fun check(permission: Permission): Permission.Status {
        return permissionChecker(permission).check()
    }

    fun request(permission: Permission) {
        permissionChecker(permission).request()
    }

    private fun permissionChecker(permission: Permission): PermissionChecker = when (permission) {
        Permission.LOCATION -> locationPermission
        Permission.CAMERA -> cameraPermission
        Permission.NOTIFICATIONS -> notificationsPermission
        else -> error("Unsupported permission: $permission")
    }
}