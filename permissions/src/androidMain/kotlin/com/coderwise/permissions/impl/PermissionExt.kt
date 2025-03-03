package com.coderwise.permissions.impl

import android.Manifest
import com.coderwise.permissions.CAMERA
import com.coderwise.permissions.LOCATION
import com.coderwise.permissions.MICROPHONE
import com.coderwise.permissions.Permission
import com.coderwise.permissions.STORAGE

fun Permission.asAndroidPermissionId(): String = when (this) {
    Permission.LOCATION -> Manifest.permission.ACCESS_FINE_LOCATION
    Permission.CAMERA -> Manifest.permission.CAMERA
    Permission.MICROPHONE -> Manifest.permission.RECORD_AUDIO
    Permission.STORAGE -> Manifest.permission.WRITE_EXTERNAL_STORAGE
    else -> error("Unknown permission")
}
