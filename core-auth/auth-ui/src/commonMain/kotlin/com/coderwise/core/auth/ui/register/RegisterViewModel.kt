package com.coderwise.core.auth.ui.register

import com.coderwise.core.auth.domain.AuthRepository
import com.coderwise.core.auth.ui.login.LoginRoute
import com.coderwise.core.domain.arch.onError
import com.coderwise.core.domain.arch.onSuccess
import com.coderwise.core.ui.arch.BaseViewModel
import com.coderwise.core.ui.arch.NavigationRouter
import com.coderwise.core.ui.arch.UiMessenger
import com.coderwise.core.ui.arch.UiNotification
import com.coderwise.core.ui.component.ProgressButtonState

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val uiMessenger: UiMessenger,
    private val navigationRouter: NavigationRouter
) : BaseViewModel<RegisterModelState, RegisterUiState>(
    initialState = RegisterModelState(),
    mapper = { it.asUiState() }
) {
    override fun onAction(action: Any) {
        when (action) {
            is RegisterAction.UserNameUpdated -> reduce { copy(userName = action.userName) }
            is RegisterAction.PasswordUpdated -> reduce { copy(password = action.password) }
            is RegisterAction.RegisterButtonClicked -> asyncAction { state ->
                reduce {
                    copy(isProgress = true)
                }
                authRepository.register(state.userName, state.password).onSuccess {
                    navigationRouter.navigateUp()
                }.onError {
                    uiMessenger.showNotification(
                        UiNotification(
                            it.message ?: "Something went wrong"
                        )
                    )
                }
                reduce {
                    copy(isProgress = false)
                }
            }
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
