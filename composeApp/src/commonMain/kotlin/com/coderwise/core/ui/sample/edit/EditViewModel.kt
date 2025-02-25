package com.coderwise.core.ui.sample.edit

import androidx.lifecycle.viewModelScope
import com.coderwise.core.data.SampleRepository
import com.coderwise.core.domain.arch.onError
import com.coderwise.core.domain.arch.onSuccess
import com.coderwise.core.ui.arch.Action
import com.coderwise.core.ui.arch.BaseViewModel
import com.coderwise.core.ui.arch.NavigationRouter
import com.coderwise.core.ui.arch.UiMessenger
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
            sampleRepository.flowById(sampleId).collect { outcome ->
                outcome.onSuccess { sample ->
                    reduce {
                        it.copy(sample = sample)
                    }
                }
            }
        }
    }

    override fun reduce(state: EditModelState, action: Action): EditModelState {
        return state
    }

    override fun handle(action: Action) {
        when (action) {
            is EditAction.ValueUpdated -> reduce {
                it.copy(sample = it.sample?.copy(value = action.value))
            }

            is EditAction.Save -> effect { state ->
                state.sample ?: return@effect
                reduce {
                    it.copy(sample = null, isProgress = true)
                }

                sampleRepository.update(state.sample).onSuccess {
                    reduce {
                        it.copy(isProgress = false)
                    }
                    effect {
                        uiMessenger.showMessage("Sample updated")
                    }
                    navigationRouter.navigateUp()
                }.onError {
                    reduce {
                        it.copy(isProgress = false)
                    }
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
