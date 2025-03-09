package com.coderwise.core.location

import kotlinx.datetime.Instant

data class GpsMessage(
    val latLon: LatLon,
    val horizontalAccuracy: Float,
    val altitude: Double,
    val verticalAccuracy: Float,
    val speed: Float,
    val speedAccuracyMps: Float,
    val bearing: Float,
    val bearingAccuracyDegrees: Float,
    val timestamp: Instant,
    val provider: String?
) {
    companion object
}