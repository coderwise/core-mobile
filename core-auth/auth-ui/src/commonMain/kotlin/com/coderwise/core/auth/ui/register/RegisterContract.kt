package com.coderwise.core.auth.ui.register

import com.coderwise.core.ui.arch.Action
import com.coderwise.core.ui.component.ProgressButtonState
import kotlinx.serialization.Serializable

@Serializable
data object RegisterRoute

data class RegisterModelState(
    val userName: String = "",
    val password: String = "",
    val isProgress: Boolean = false
)

data class RegisterUiState(
    val userName: String,
    val password: String,
    val registerButtonState: ProgressButtonState
)

sealed interface RegisterAction : Action {
    data class UserNameUpdated(val userName: String) : RegisterAction
    data class PasswordUpdated(val password: String) : RegisterAction

    data object RegisterButtonClicked : RegisterAction
}