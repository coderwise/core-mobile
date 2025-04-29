package com.coderwise.core.permissions.impl

import com.coderwise.core.permissions.CAMERA
import com.coderwise.core.permissions.Permission
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVAuthorizationStatusRestricted
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType

class CameraPermissionChecker(
    private val onStatusUpdate: (Permission, Permission.Status) -> Unit
) : PermissionChecker {
    override fun check(): Permission.Status {
        val authorizationStatus = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)
        return when (authorizationStatus) {
            AVAuthorizationStatusAuthorized -> Permission.Status.GRANTED
            AVAuthorizationStatusNotDetermined -> Permission.Status.PENDING
            AVAuthorizationStatusDenied -> Permission.Status.DENIED
            AVAuthorizationStatusRestricted -> Permission.Status.GRANTED
            else -> error("unknown authorization status $authorizationStatus")
        }
    }

    override fun request() {
        AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { isGranted ->
            onStatusUpdate(
                Permission.CAMERA,
                if (isGranted)
                    Permission.Status.GRANTED
                else
                    Permission.Status.DENIED
            )
        }
    }
}