package com.coderwise.core.ui.sample

import androidx.lifecycle.viewModelScope
import com.coderwise.core.data.SampleRepository
import com.coderwise.core.domain.arch.onSuccess
import com.coderwise.core.ui.arch.BaseViewModel
import kotlinx.coroutines.launch


class SampleViewModel(
    private val sampleRepository: SampleRepository
) : BaseViewModel<SampleModelState, SampleUiState>(
    initialState = SampleModelState(),
    mapper = { it.asUiState() }
) {
    init {
        viewModelScope.launch {
            sampleRepository.flow.collect { outcome ->
                outcome.onSuccess { sample ->
                    reduce {
                        it.copy(samples = listOf(sample))
                    }
                }
            }
        }
    }

    fun onAction(action: SampleAction) {
        when (action) {
            is SampleAction.ItemClicked -> {}
        }
    }
}

private fun SampleModelState.asUiState() = SampleUiState(
    items = samples?.map { it.value } ?: emptyList()
)
