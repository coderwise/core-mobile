package com.coderwise.core.sample.ui.location

import com.coderwise.core.location.LocationService
import com.coderwise.core.ui.arch.BaseViewModel
import kotlin.time.Duration.Companion.seconds

class LocationViewModel(
    private val locationService: LocationService
) : BaseViewModel<LocationUiState, LocationUiState>(
    initialState = LocationUiState(),
    mapper = { it }
) {
    init {
        asyncAction {
            locationService.gpsMessages.collect {
                reduce { copy(gpsMessage = it) }
            }
        }
        asyncAction {
            locationService.status.collect {
                reduce { copy(locationServiceStatus = it) }
            }
        }
        asyncAction {
            locationService.configure(minTime = 1.seconds, minDistance = 2.0f)
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

            is LocationAction.OnStartClicked -> asyncAction {
                locationService.start()
            }

            is LocationAction.OnStopClicked -> asyncAction {
                locationService.stop()
            }

            is LocationAction.OnConfigureClicked -> asyncAction {
                locationService.configure(
                    it.minTime.toLong().seconds, it.minDistance.toFloat()
                )
            }
        }
    }
}
