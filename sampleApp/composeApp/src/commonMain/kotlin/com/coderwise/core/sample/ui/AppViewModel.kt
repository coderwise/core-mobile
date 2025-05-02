package com.coderwise.core.sample.ui

import com.coderwise.core.sample.data.sample.SampleRepository
import com.coderwise.core.ui.arch.SimpleViewModel
import com.coderwise.core.ui.arch.UiMessenger
import com.coderwise.core.ui.arch.UiNotification

class AppViewModel(
    sampleRepository: SampleRepository,
    private val uiMessenger: UiMessenger,
) : SimpleViewModel<Unit>(initialState = Unit) {
    init {
        asyncAction {
            sampleRepository.syncStatus.collect {
                it.toString()
            }
        }
    }

    override fun onAction(action: Any) {
        when (action) {
            is Action.OnDestinationChanged -> asyncAction {
                uiMessenger.showNotification(UiNotification(action.route ?: "No route"))
            }

            else -> {
                throw IllegalArgumentException("Unknown action: $action")
            }
        }
    }

    sealed interface Action {
        data class OnDestinationChanged(val route: String?) : Action
    }
}
