package com.coderwise.core.ui.arch

open class SimpleViewModel<State>(
    initialState: State
) : BaseViewModel<State, State>(
    initialState = initialState,
    mapper = { it }
)