package com.coderwise.core.sample.ui.list

import com.coderwise.core.sample.data.Sample
import com.coderwise.core.ui.arch.Action

data class ListUiState(
    val items: List<Item>
) {
    data class Item(
        val id: Int,
        val value: String
    )
}

data class ListModelState(
    val samples: List<Sample>? = null
)

sealed interface ListAction : Action {
    data class OnItemClicked(val id: Int) : ListAction
    data object OnAddButtonClicked : ListAction
    data object OnAccountClicked : ListAction
}