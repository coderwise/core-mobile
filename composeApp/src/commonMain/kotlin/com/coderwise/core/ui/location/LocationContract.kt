package com.coderwise.core.ui.location

import com.coderwise.core.location.GpsMessage
import kotlinx.serialization.Serializable

@Serializable
data object LocationRoute

data class LocationUiState(
    val gpsMessage: GpsMessage? = null,
    val minTime: String = "1",
    val minDistance: String = "2"
)

sealed interface LocationAction {
    data class OnMinTimeChanged(val minTime: String) : LocationAction
    data class OnMinDistanceChanged(val minDistance: String) : LocationAction
}