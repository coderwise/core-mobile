package com.coderwise.core.location

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class GpsMessage(
    val latLon: LatLon,
    val horizontalAccuracy: Float = 0f,
    val altitude: Double = 0.0,
    val verticalAccuracy: Float = 0f,
    val speed: Float = 0f,
    val speedAccuracyMps: Float = 0f,
    val bearing: Float = 0f,
    val bearingAccuracyDegrees: Float = 0f,
    val timestamp: Instant,
    val provider: String? = null
) {
    companion object
}