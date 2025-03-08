package com.coderwise.core.ui.location

import com.coderwise.core.location.LocationService
import com.coderwise.core.ui.arch.BaseViewModel

class LocationViewModel(
    private val locationService: LocationService
) : BaseViewModel<LocationUiState, LocationUiState>(
    initialState = LocationUiState(),
    mapper = { it }
) {
    init {
        asyncAction {
            locationService.coordinates.collect {
                reduce { copy(gpsMessage = it) }
            }
        }
    }

    override fun onAction(action: Any) {
        when (action) {
            is LocationAction.OnMinDistanceChanged -> reduce {
                copy(minDistance = action.minDistance)
            }

            is LocationAction.OnMinTimeChanged -> reduce {
                copy(minTime = action.minTime)
            }
        }
    }
}
