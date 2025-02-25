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

    protected fun reduce(reducer: ModelState.() -> ModelState) {
        modelState.reduce(reducer)
    }

    protected open fun reduce(state: ModelState, action: Action): ModelState = state

    protected fun asyncAction(block: suspend (ModelState) -> Unit) {
        val modelState = modelState.value
        viewModelScope.launch {
            block(modelState)
        }
    }

    protected fun <TState> TState.sideEffect(block: suspend () -> Unit): TState {
        viewModelScope.launch {
            block()
        }
        return this
    }

    fun dispatch(action: Action) {
        modelState.reduce { reduce(it, action) }
        viewModelScope.launch {
            onAction(action)
        }
    }

    protected open fun onAction(action: Action) {}
}