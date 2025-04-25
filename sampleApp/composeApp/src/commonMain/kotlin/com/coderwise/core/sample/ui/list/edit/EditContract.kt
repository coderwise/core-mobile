package com.coderwise.core.sample.ui.list.edit

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.coderwise.core.sample.data.Sample
import com.coderwise.core.ui.arch.Action
import com.coderwise.core.ui.component.scaffold
import kotlinx.serialization.Serializable

@Serializable
data class EditRoute(val id: Int)

fun NavGraphBuilder.editScreen() {
    composable<EditRoute> {
        scaffold {
            showBackNavigation = true
            topBarTitle = "Edit"
            topBarActions = emptyList()
            showBottomBar = false
        }

        EditScreen()
    }
}

data class EditModelState(
    val sample: Sample? = null,
    val isProgress: Boolean = false
)

data class EditUiState(
    val sampleValue: String,
    val saveEnabled: Boolean,
    val valueEnabled: Boolean
)

interface EditAction : Action {
    object Save : EditAction

    data class ValueUpdated(val value: String) : EditAction
}