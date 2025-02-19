package com.coderwise.core.ui.sample

import androidx.lifecycle.viewModelScope
import com.coderwise.core.data.SampleRepository
import com.coderwise.core.domain.arch.onSuccess
import com.coderwise.core.ui.arch.Action
import com.coderwise.core.ui.arch.BaseViewModel
import com.coderwise.core.ui.arch.NavigationRouter
import com.coderwise.core.ui.arch.UiMessenger
import kotlinx.coroutines.launch


class SampleViewModel(
    private val navRouter: NavigationRouter,
    private val uiMessenger: UiMessenger,
    private val sampleRepository: SampleRepository
) : BaseViewModel<SampleModelState, SampleUiState>(
    initialState = SampleModelState(),
    mapper = { it.asUiState() }
) {
    init {
        viewModelScope.launch {
            sampleRepository.flow.collect { outcome ->
                outcome.onSuccess { samples ->
                    reduce {
                        it.copy(samples = samples)
                    }
                }
            }
        }
    }

    override fun dispatch(action: Action) {
        when (action) {
            is SampleAction.ItemClicked -> effect { state ->
                uiMessenger.showMessage("Item clicked: ${state.samples?.get(action.index)?.value}")
                //navRouter.navigate(NavDestination.Detail(action.item))
            }
        }
    }
}

private fun SampleModelState.asUiState() = SampleUiState(
    items = samples?.map { it.value } ?: emptyList()
)
