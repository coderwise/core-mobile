package com.coderwise.core.ui.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

open class BaseViewModel<ModelState, UiState>(
    initialState: ModelState,
    mapper: (ModelState) -> UiState
) : ViewModel() {
    protected val modelState = ReducerStateFlow(initialState, viewModelScope)

    val uiState = modelState
        .onStart { onStart() }
        .map(mapper)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds),
            initialValue = mapper(initialState)
        )

    protected open fun onStart() {}

    protected fun reduce(reducer: ModelState.() -> ModelState) {
        modelState.reduce(reducer)
    }

    protected open fun reduce(state: ModelState, action: Any): ModelState = state

    protected fun asyncAction(block: suspend (ModelState) -> Unit): Job {
        val modelState = modelState.value
        return viewModelScope.launch {
            block(modelState)
        }
    }

    protected fun <State> State.sideEffect(block: suspend (State) -> Unit): State {
        viewModelScope.launch {
            block(this@sideEffect)
        }
        return this
    }

    fun <T : Any> dispatch(action: T) {
        modelState.reduce { reduce(it, action) }
        viewModelScope.launch {
            onAction(action)
        }
    }

    protected open fun onAction(action: Any) {}
}