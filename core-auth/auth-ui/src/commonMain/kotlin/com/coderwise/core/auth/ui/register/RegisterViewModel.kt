package com.coderwise.core.auth.ui.register

import com.coderwise.core.ui.arch.BaseViewModel
import com.coderwise.core.ui.component.ProgressButtonState

class RegisterViewModel : BaseViewModel<RegisterModelState, RegisterUiState>(
    initialState = RegisterModelState(),
    mapper = { it.asUiState() }
) {
    override fun onAction(action: Any) {
        when (action) {
            is RegisterAction.UserNameUpdated -> reduce { copy(userName = action.userName) }
            is RegisterAction.PasswordUpdated -> reduce { copy(password = action.password) }
            is RegisterAction.RegisterButtonClicked -> reduce { copy(isProgress = true) }
        }
    }
}

private fun RegisterModelState.asUiState() = RegisterUiState(
    userName = userName,
    password = password,
    registerButtonState = when {
        userName.isEmpty() -> ProgressButtonState.Disabled
        password.isEmpty() -> ProgressButtonState.Disabled
        isProgress -> ProgressButtonState.Progress
        else -> ProgressButtonState.Button
    },
    isLoading = isProgress
)
