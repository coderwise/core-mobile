package com.coderwise.core.ui.sample

import com.coderwise.core.data.Sample
import com.coderwise.core.ui.arch.Action

data class SampleUiState(
    val items: List<Item>
) {
    data class Item(
        val id: Int,
        val value: String
    )
}

data class SampleModelState(
    val samples: List<Sample>? = null
)

sealed interface SampleAction : Action {
    data class ItemClicked(val id: Int) : SampleAction
    data object AddButtonClicked : SampleAction
}