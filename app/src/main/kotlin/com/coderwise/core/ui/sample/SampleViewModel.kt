package com.coderwise.core.ui.sample

import androidx.lifecycle.viewModelScope
import com.coderwise.core.data.SampleRepository
import com.coderwise.core.domain.arch.onSuccess
import com.coderwise.core.ui.arch.BaseViewModel
import com.coderwise.core.ui.arch.NavigationRouter
import kotlinx.coroutines.launch


class SampleViewModel(
    private val navRouter: NavigationRouter,
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

    fun onAction(action: SampleAction) {
        when (action) {
            is SampleAction.ItemClicked -> effect { state ->
                //navRouter.navigate(NavDestination.Detail(action.item))
            }
        }
    }
}

private fun SampleModelState.asUiState() = SampleUiState(
    items = samples?.map { it.value } ?: emptyList()
)
