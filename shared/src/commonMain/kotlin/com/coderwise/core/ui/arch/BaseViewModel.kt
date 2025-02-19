package com.coderwise.core.ui.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

open class BaseViewModel<ModelState, UiState>(
    initialState: ModelState,
    mapper: (ModelState) -> UiState
) : ViewModel() {
    private val modelState = ReducerStateFlow(initialState, viewModelScope)

    val uiState = modelState.map(mapper).stateIn(viewModelScope, Eagerly, mapper(initialState))

    protected fun reduce(reducer: Reducer<ModelState>) {
        modelState.reduce(reducer)
    }

    protected fun effect(effect: suspend (ModelState) -> Unit) {
        viewModelScope.launch {
            effect(modelState.value)
        }
    }
}