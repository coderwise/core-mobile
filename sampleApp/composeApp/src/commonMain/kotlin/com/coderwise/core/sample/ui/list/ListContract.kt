package com.coderwise.core.sample.ui.list

import androidx.compose.runtime.Immutable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.coderwise.core.sample.data.Sample
import com.coderwise.core.ui.arch.Action
import kotlinx.serialization.Serializable

@Serializable
data object ListRoute

fun NavGraphBuilder.listScreen() {
    composable<ListRoute> {
        ListScreen()
    }
}

@Immutable
data class ListUiState(
    val items: List<Item>
) {
    data class Item(
        val id: Int,
        val value: String
    )
}

data class ListModelState(
    val samples: List<Sample>? = null,
    val isAuthenticated: Boolean = false
)

sealed interface ListAction : Action {
    data class OnItemClicked(val id: Int) : ListAction
    data object OnAddButtonClicked : ListAction
    data object OnAccountClicked : ListAction
}