package com.coderwise.core.sample.ui.list.edit

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.coderwise.core.sample.data.sample.SampleRepository
import com.coderwise.core.domain.arch.onError
import com.coderwise.core.domain.arch.onSuccess
import com.coderwise.core.domain.repository.NetworkError
import com.coderwise.core.ui.arch.BaseViewModel
import com.coderwise.core.ui.arch.NavigationRouter
import com.coderwise.core.ui.arch.UiMessenger
import com.coderwise.core.ui.arch.UiNotification

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val uiMessenger: UiMessenger,
    private val navigationRouter: NavigationRouter,
    private val sampleRepository: SampleRepository
) : BaseViewModel<EditModelState, EditUiState>(
    initialState = EditModelState(),
    mapper = { it.asUiState() }
) {
    val route = savedStateHandle.toRoute<EditRoute>()

    init {
        asyncAction {
            sampleRepository.flowById(route.id).collect { outcome ->
                outcome.onSuccess { sample ->
                    reduce { copy(sample = sample) }
                }
            }
        }
    }

    override fun onAction(action: Any) {
        when (action) {
            is EditAction.OnValueUpdated -> reduce {
                copy(sample = sample?.copy(value = action.value))
            }

            is EditAction.OnSave -> asyncAction { state ->
                state.sample ?: return@asyncAction
                reduce { copy(isProgress = true) }

                sampleRepository.update(state.sample).onSuccess {
                    reduce { copy(isProgress = false) }
                    asyncAction {
                        uiMessenger.showNotification(UiNotification("Sample updated"))
                    }
                    navigationRouter.navigateUp()
                }.onError {
                    reduce { copy(isProgress = false) }
                    uiMessenger.showNotification(UiNotification(it.message))
                    if (it.error is NetworkError) {
                        navigationRouter.navigateUp()
                    }
                }
            }

            is EditAction.OnDelete -> asyncAction { state ->
                state.sample ?: return@asyncAction
                reduce { copy(sample = null, isProgress = true) }

                sampleRepository.delete(state.sample.id).onSuccess {
                    reduce { copy(isProgress = false) }
                    asyncAction {
                        uiMessenger.showNotification(UiNotification("Sample deleted"))
                    }
                    navigationRouter.navigateUp()
                }.onError {
                    reduce { copy(isProgress = false) }
                    uiMessenger.showNotification(UiNotification(it.message))
                }
            }
        }
    }
}

private fun EditModelState.asUiState() = EditUiState(
    sampleId = sample?.id?.toString() ?: "...",
    sampleValue = sample?.value ?: "...",
    saveEnabled = sample != null && sample.value.isNotBlank() && !isProgress,
    valueEnabled = !isProgress
)
