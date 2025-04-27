package com.coderwise.core.sample.ui.profile

import com.coderwise.core.auth.domain.SessionRepository
import com.coderwise.core.ui.arch.BaseViewModel
import com.coderwise.core.ui.arch.NavigationRouter

class ProfileViewModel(
    private val navigationRouter: NavigationRouter,
    private val sessionRepository: SessionRepository
) : BaseViewModel<ProfileModelState, ProfileUiState>(
    initialState = ProfileModelState(),
    mapper = { it.asUiState() }
) {
    init {
        asyncAction {
            sessionRepository.session.collect {
                reduce { copy(session = it) }
            }
        }
    }

    override fun onAction(action: Any) {
        when (action) {
            is ProfileAction.Logout -> asyncAction {
                sessionRepository.clear()
                navigationRouter.navigateUp()
            }
        }
    }
}

private fun ProfileModelState.asUiState() = ProfileUiState(
    name = session?.userName ?: ""
)
