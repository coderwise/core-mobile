package com.coderwise.core.ui.arch

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ReducerStateFlowTest {

//    private val testScope = CoroutineScope(Dispatchers.Main)
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @BeforeTest
//    fun setUp() {
//        Dispatchers.setMain(Dispatchers.Unconfined)
//    }
//
//    @Test
//    fun whenreduce() = runTest {
//        val stateFlow = ReducerStateFlow(0, testScope)
//
//        stateFlow.reduce { it + 1 }
//
//        stateFlow.take(1).collect {
//            assertEquals(1, stateFlow.value)
//        }
//    }
}