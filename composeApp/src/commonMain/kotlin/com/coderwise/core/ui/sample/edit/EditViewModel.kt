package com.coderwise.core.ui.sample.edit

import com.coderwise.core.data.SampleRepository
import com.coderwise.core.domain.arch.onError
import com.coderwise.core.domain.arch.onSuccess
import com.coderwise.core.ui.arch.Action
import com.coderwise.core.ui.arch.BaseViewModel
import com.coderwise.core.ui.arch.NavigationRouter
import com.coderwise.core.ui.arch.UiMessenger

class EditViewModel(
    private val sampleId: String,
    private val uiMessenger: UiMessenger,
    private val navigationRouter: NavigationRouter,
    private val sampleRepository: SampleRepository
) : BaseViewModel<EditModelState, EditUiState>(
    initialState = EditModelState(),
    mapper = { it.asUiState() }
) {
    init {
        asyncAction {
            sampleRepository.flowById(sampleId).collect { outcome ->
                outcome.onSuccess { sample ->
                    reduce { copy(sample = sample) }
                }
            }
        }
    }

    override fun onAction(action: Action) {
        when (action) {
            is EditAction.ValueUpdated -> reduce {
                copy(sample = sample?.copy(value = action.value))
            }

            is EditAction.Save -> asyncAction { state ->
                state.sample ?: return@asyncAction
                reduce { copy(sample = null, isProgress = true) }

                sampleRepository.update(state.sample).onSuccess {
                    reduce { copy(isProgress = false) }
                    asyncAction {
                        uiMessenger.showMessage("Sample updated")
                    }
                    navigationRouter.navigateUp()
                }.onError {
                    reduce { copy(isProgress = false) }
                    uiMessenger.showMessage("Error updating sample")
                }
            }
        }
    }
}

private fun EditModelState.asUiState() = EditUiState(
    sampleValue = sample?.value ?: "...",
    saveEnabled = sample != null && sample.value.isNotBlank() && !isProgress,
    valueEnabled = !isProgress
)
