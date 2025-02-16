package com.coderwise.core.ui.sample

import com.coderwise.core.data.Sample

data class SampleUiState(
    val items: List<String>
)

data class SampleModelState(
    val samples: List<Sample>? = null
)

sealed interface SampleAction {
    data class ItemClicked(val index: Int) : SampleAction
}