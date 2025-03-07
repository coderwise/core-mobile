package com.coderwise.core.permissions.impl

import android.Manifest
import com.coderwise.core.permissions.CAMERA
import com.coderwise.core.permissions.LOCATION
import com.coderwise.core.permissions.MICROPHONE
import com.coderwise.core.permissions.Permission
import com.coderwise.core.permissions.STORAGE

fun Permission.asAndroidPermissionId(): String = when (this) {
    Permission.LOCATION -> Manifest.permission.ACCESS_FINE_LOCATION
    Permission.CAMERA -> Manifest.permission.CAMERA
    Permission.MICROPHONE -> Manifest.permission.RECORD_AUDIO
    Permission.STORAGE -> Manifest.permission.WRITE_EXTERNAL_STORAGE
    else -> error("Unknown permission")
}
