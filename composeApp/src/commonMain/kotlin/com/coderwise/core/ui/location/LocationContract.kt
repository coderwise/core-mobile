package com.coderwise.core.ui.location

import com.coderwise.core.location.GpsMessage
import com.coderwise.core.location.LocationService
import kotlinx.serialization.Serializable

@Serializable
data object LocationRoute

data class LocationUiState(
    val gpsMessage: GpsMessage? = null,
    val minTime: String = "1",
    val minDistance: String = "2",
    val locationServiceStatus: LocationService.Status? = null
)

sealed interface LocationAction {
    data object OnStartClicked : LocationAction
    data object OnStopClicked : LocationAction
    data object OnConfigureClicked : LocationAction

    data class OnMinTimeChanged(val minTime: String) : LocationAction
    data class OnMinDistanceChanged(val minDistance: String) : LocationAction
}