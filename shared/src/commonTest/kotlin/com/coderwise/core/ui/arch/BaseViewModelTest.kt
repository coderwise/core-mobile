package com.coderwise.core.ui.arch

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BaseViewModelTest {
    @Test
    fun whenReduceIsCalled() = runTest {
        val viewModel = BaseViewModel(0) { it }

        val uiState = viewModel.uiState.value

        assertEquals(0, uiState)
    }
}