package com.coderwise.core.auth.ui.login

import com.coderwise.core.auth.domain.User
import com.coderwise.core.ui.arch.Action
import com.coderwise.core.ui.component.ProgressButtonState
import kotlinx.serialization.Serializable

@Serializable
data class LoginRoute(val onSuccessRoute: String)

data class LoginModelState(
    val user: User? = null,
    val userName: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val isProgress: Boolean = false
)

data class LoginUiState(
    val userName: String,
    val password: String,
    val rememberMe: Boolean,
    val loginButtonState: ProgressButtonState,
    val isLoading: Boolean
)

sealed interface LoginAction : Action {
    data class UserNameUpdated(val userName: String) : LoginAction

    data class PasswordUpdated(val password: String) : LoginAction

    data class RememberMeUpdated(val rememberMe: Boolean) : LoginAction

    data object LoginButtonClicked : LoginAction
    data object RegisterButtonClicked : LoginAction
    data object ForgotPasswordButtonClicked : LoginAction
}