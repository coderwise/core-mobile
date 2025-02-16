package com.coderwise.core.ui.arch

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn

typealias Reducer<State> = (State) -> State

class ReducerStateFlow<State>(
    initialState: State,
    scope: CoroutineScope
) : StateFlow<State> {

    private val actions = Channel<Reducer<State>>(BUFFERED)

    private val stateFlow = actions
        .receiveAsFlow()
        .runningFold(initialState) { acc, reducer -> reducer(acc)}
        .stateIn(scope, Eagerly, initialState)

    override val replayCache: List<State> = stateFlow.replayCache
    override val value: State = stateFlow.value

    override suspend fun collect(collector: FlowCollector<State>): Nothing {
        stateFlow.collect(collector)
    }

    fun reduce(reducer: Reducer<State>) {
        actions.trySend(reducer)
    }
}