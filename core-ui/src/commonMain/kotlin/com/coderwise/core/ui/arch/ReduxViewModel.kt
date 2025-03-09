package com.coderwise.core.ui.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class ReduxStore<State>(
    initialState: State,
    scope: CoroutineScope = CoroutineScope(Job())
) {
    inner class InlineReducerAction(
        val reducer: (State) -> State
    ) : Action

    private val actions = Channel<Action>(BUFFERED)

    private val reducers = mutableListOf<(State, Action) -> State>().apply {
        // TODO doesn't compile in kotlin multiplatform library
//        add { state: State, action: Action ->
//            if (action is ReduxStore<State>.InlineReducerAction)
//                action.reducer(state)
//            else
//                state
//        }
    }

    val state = actions
        .receiveAsFlow()
        .runningFold(initialState) { state, action ->
            reducers.fold(state) { acc, reducer -> reducer(acc, action) }
        }.stateIn(scope, SharingStarted.Eagerly, initialState)

    fun dispatch(action: Action) {
        actions.trySend(action)
    }

    fun addReducer(reducer: (State, Action) -> State) {
        reducers.add(reducer)
    }

    fun reduce(reducer: (State) -> State) {
        dispatch(InlineReducerAction(reducer))
    }
}

open class ReduxViewModel<ModelState, UiState>(
    initialState: ModelState,
    mapper: (ModelState) -> UiState
) : ViewModel() {
    private val store = ReduxStore<ModelState>(initialState).apply {
        addReducer(::reduce)
    }

    val uiState: StateFlow<UiState> = store.state.map {
        mapper(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = mapper(initialState)
    )

    fun dispatch(action: Action) {
        store.dispatch(action)
        viewModelScope.launch {
            onAction(action)
        }
    }

    protected open fun reduce(state: ModelState, action: Action): ModelState = state

    protected open fun onAction(action: Action) {}

    protected fun reduce(reducer: ModelState.() -> ModelState) {
        store.reduce(reducer)
    }

    protected fun <TState> TState.effect(block: suspend () -> Unit): TState {
        viewModelScope.launch {
            block()
        }
        return this
    }

    protected fun asyncAction(block: suspend (ModelState) -> Unit) {
        val state = store.state.value
        viewModelScope.launch {
            block(state)
        }
    }
}
