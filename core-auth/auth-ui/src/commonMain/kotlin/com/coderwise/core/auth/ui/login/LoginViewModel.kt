package com.coderwise.core.auth.ui.login

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.coderwise.core.auth.domain.AuthRepository
import com.coderwise.core.auth.domain.SessionRepository
import com.coderwise.core.auth.ui.recover.RecoverRoute
import com.coderwise.core.auth.ui.register.RegisterRoute
import com.coderwise.core.domain.arch.onError
import com.coderwise.core.domain.arch.onSuccess
import com.coderwise.core.ui.arch.BaseViewModel
import com.coderwise.core.ui.arch.NavigationRouter
import com.coderwise.core.ui.arch.UiMessenger
import com.coderwise.core.ui.arch.UiNotification
import com.coderwise.core.ui.component.ProgressButtonState

class LoginViewModel(
    savedStateHandle: SavedStateHandle,
    private val navigationRouter: NavigationRouter,
    private val uiMessenger: UiMessenger,
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository
) : BaseViewModel<LoginModelState, LoginUiState>(
    initialState = LoginModelState(),
    mapper = { it.asUiState() }
) {
    private val args = savedStateHandle.toRoute<LoginRoute>()

    init {
        asyncAction {
            reduce { copy(userName = args.userName, password = args.password) }
        }
        asyncAction {
            sessionRepository.session.collect {
                reduce {
                    copy(
                        userName = it.userName ?: "",
                        rememberMe = it.rememberMe
                    )
                }
            }
        }
    }

    override fun onAction(action: Any) {
        when (action) {
            is LoginAction.UserNameUpdated -> reduce {
                copy(userName = action.userName)
            }

            is LoginAction.PasswordUpdated -> reduce {
                copy(password = action.password)
            }

            is LoginAction.LoginButtonClicked -> onLogin()

            is LoginAction.RegisterButtonClicked -> asyncAction {
                navigationRouter.navigate(RegisterRoute)
            }

            is LoginAction.ForgotPasswordButtonClicked -> asyncAction {
                navigationRouter.navigate(RecoverRoute)
            }

            is LoginAction.RememberMeUpdated -> reduce {
                copy(rememberMe = action.rememberMe)
            }

            else -> {
                throw IllegalArgumentException("Unknown action: $action")
            }
        }
    }

    private fun onLogin() = asyncAction { state ->
        reduce {
            copy(isProgress = true)
        }
        authRepository.login(state.userName, state.password).onSuccess {
            sessionRepository.setRememberMe(state.rememberMe)
            sessionRepository.setToken(it.token)
            navigationRouter.navigate(args.onSuccessRouteId, false)
        }.onError {
            reduce {
                copy(isProgress = false)
            }
            uiMessenger.showNotification(UiNotification(it.message ?: "Something went wrong"))
        }
    }
}

private fun LoginModelState.asUiState() = LoginUiState(
    userName = userName,
    password = password,
    rememberMe = rememberMe,
    loginButtonState = when {
        userName.isEmpty() -> ProgressButtonState.Disabled
        password.isEmpty() -> ProgressButtonState.Disabled
        isProgress -> ProgressButtonState.Progress
        else -> ProgressButtonState.Button
    },
    isLoading = isProgress
)
