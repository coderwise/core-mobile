package com.coderwise.core.ui.sample

import androidx.lifecycle.viewModelScope
import com.coderwise.core.data.Sample
import com.coderwise.core.data.SampleRepository
import com.coderwise.core.domain.arch.onSuccess
import com.coderwise.core.ui.arch.Action
import com.coderwise.core.ui.arch.NavigationRouter
import com.coderwise.core.ui.arch.ReduxViewModel
import com.coderwise.core.ui.arch.UiMessenger
import com.coderwise.core.ui.sample.edit.EditRoute
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class SampleViewModel(
    private val navRouter: NavigationRouter,
    private val uiMessenger: UiMessenger,
    private val sampleRepository: SampleRepository
) : ReduxViewModel<SampleModelState, SampleUiState>(
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

    override fun reduce(state: SampleModelState, action: Action): SampleModelState {
        return state
    }

    override fun handle(action: Action) {
        when (action) {
            is SampleAction.ItemClicked -> effect { state ->
                uiMessenger.showMessage("Item clicked: ${action.id}")
                navRouter.navigate(EditRoute(action.id))
            }

            is SampleAction.AddButtonClicked -> effect { state ->
                sampleRepository.update(
                    Sample(
                        id = Clock.System.now().epochSeconds.toString(),
                        value = "New sample"
                    )
                )
            }
        }
    }
}

private fun SampleModelState.asUiState() = SampleUiState(
    items = samples?.map { SampleUiState.Item(it.id, it.value) } ?: emptyList()
)
