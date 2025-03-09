package com.coderwise.core.permissions.impl

import com.coderwise.core.permissions.PermissionRequester
import platform.CoreLocation.CLLocationManager

class LocationPermissionRequester : PermissionRequester {
    private val locationManager = CLLocationManager()
    override fun request() {
        locationManager.requestWhenInUseAuthorization()
    }
}