package com.coderwise.core.sample.ui.list.edit

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.coderwise.core.sample.data.sample.Sample
import com.coderwise.core.ui.arch.Action
import kotlinx.serialization.Serializable

@Serializable
data class EditRoute(val id: Int)

fun NavGraphBuilder.editScreen() {
    composable<EditRoute> {
        EditScreen()
    }
}

data class EditModelState(
    val sample: Sample? = null,
    val isProgress: Boolean = false
)

data class EditUiState(
    val sampleId: String,
    val sampleValue: String,
    val saveEnabled: Boolean,
    val valueEnabled: Boolean
)

interface EditAction : Action {
    data object OnSave : EditAction
    data object OnDelete : EditAction

    data class OnValueUpdated(val value: String) : EditAction
}