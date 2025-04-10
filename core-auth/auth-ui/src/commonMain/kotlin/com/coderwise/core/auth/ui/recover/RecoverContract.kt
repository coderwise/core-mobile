package com.coderwise.core.auth.ui.recover

import com.coderwise.core.ui.arch.Action
import kotlinx.serialization.Serializable

@Serializable
data object RecoverRoute

data class RecoverModelState(
    val isProgress: Boolean = false,
)

data class RecoverUiState(
    val isProgress: Boolean = false,
)

sealed interface RecoverAction : Action {

}