package com.coderwise.core.permissions.impl

import com.coderwise.core.permissions.CAMERA
import com.coderwise.core.permissions.LOCATION
import com.coderwise.core.permissions.Permission

class IosPermissionRequestRouter {
    fun request(permission: Permission) {
        val requester = when (permission) {
            Permission.LOCATION -> LocationPermissionRequester()
            Permission.CAMERA -> CameraPermissionRequester()
            else -> error("Unsupported permission: $permission")
        }
        requester.request()
    }
}