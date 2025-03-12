package com.coderwise.core.ui.sample

import com.coderwise.core.data.Sample
import com.coderwise.core.data.SampleRepository
import com.coderwise.core.domain.arch.onSuccess
import com.coderwise.core.ui.Edit
import com.coderwise.core.ui.arch.BaseViewModel
import com.coderwise.core.ui.arch.NavigationRouter
import com.coderwise.core.ui.arch.UiMessenger
import kotlinx.datetime.Clock

class SampleViewModel(
    private val navRouter: NavigationRouter,
    private val uiMessenger: UiMessenger,
    private val sampleRepository: SampleRepository
) : BaseViewModel<SampleModelState, SampleUiState>(
    initialState = SampleModelState(),
    mapper = { it.asUiState() }
) {
    init {
        asyncAction {
            sampleRepository.flow.collect { outcome ->
                outcome.onSuccess { samples ->
                    reduce {
                        it.copy(samples = samples)
                    }
                }
            }
        }
    }

    override fun onAction(action: Any) {
        when (action) {
            is SampleAction.ItemClicked -> asyncAction {
                uiMessenger.showMessage("Item clicked: ${action.id}")
                navRouter.navigate(Edit(action.id))
            }

            is SampleAction.AddButtonClicked -> asyncAction {
                sampleRepository.update(
                    Sample(
                        id = Clock.System.now().epochSeconds.toInt(),
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
