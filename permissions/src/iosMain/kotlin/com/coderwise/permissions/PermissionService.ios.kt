package com.coderwise.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.koin.compose.koinInject
import platform.CoreLocation.CLLocationManager

@Composable
actual fun ProcessPermissionRequestEffect(
    permission: Permission
) {
    val permissionService = koinInject<PermissionService>()

    LaunchedEffect(permission) {
        val locationManager = CLLocationManager()
        permissionService.requests.collect {
            locationManager.requestWhenInUseAuthorization()
        }
    }
}