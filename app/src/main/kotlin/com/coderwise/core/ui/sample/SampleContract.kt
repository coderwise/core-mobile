package com.coderwise.core.ui.sample

data class SampleUiState(
    val test: String = "test"
)

sealed interface SampleAction {
    data object ButtonClicked : SampleAction
}