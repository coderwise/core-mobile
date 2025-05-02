package com.coderwise.core.sample.ui.location

import androidx.compose.runtime.Immutable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.coderwise.core.location.GpsMessage
import com.coderwise.core.location.LocationService
import com.coderwise.core.ui.component.scaffold
import kotlinx.serialization.Serializable

@Serializable
data object LocationRoute

fun NavGraphBuilder.locationScreen() {
    composable<LocationRoute> {
        scaffold {
            showBackNavigation = false
            topBarTitle = "Location"
            showBottomBar = true
            topBarActions = listOf()
        }
        LocationScreen()
    }
}

@Immutable
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