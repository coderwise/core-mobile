package com.coderwise.core.ui.sample

import androidx.lifecycle.viewModelScope
import com.coderwise.core.data.SampleRepository
import com.coderwise.core.ui.arch.BaseViewModel
import kotlinx.coroutines.launch

data class SampleModelState(
    val test: String = ""
)

class SampleViewModel(
    private val sampleRepository: SampleRepository
) : BaseViewModel<SampleModelState, SampleUiState>(
    initialState = SampleModelState(),
    mapper = { SampleUiState() }
) {
    init {
        viewModelScope.launch {
            sampleRepository.flow.collect {
                reduce {
                    it.copy(test = it.test)
                }
            }
        }
    }

    fun onAction(action: SampleAction) {
        when (action) {
            SampleAction.ButtonClicked -> {
                reduce {
                    it.copy(test = "test")
                }
                effect {

                }
            }
        }
    }
}