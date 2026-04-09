package com.coderwise.core.sample.ui.profile

import com.coderwise.core.auth.domain.SessionRepository
import com.coderwise.core.domain.arch.onSuccess
import com.coderwise.core.sample.data.profile.Profile
import com.coderwise.core.sample.data.profile.ProfileRepository
import com.coderwise.core.ui.arch.BaseViewModel
import com.coderwise.core.ui.arch.NavigationRouter

class ProfileViewModel(
    private val navigationRouter: NavigationRouter,
    private val sessionRepository: SessionRepository,
    private val profileRepository: ProfileRepository
) : BaseViewModel<ProfileModelState, ProfileUiState>(
    initialState = ProfileModelState(),
    mapper = { it.asUiState() }
) {
    init {
        asyncAction {
            sessionRepository.session.collect { session ->
                reduce { copy(session = session) }
                asyncAction { profileRepository.update(Profile(session.userName ?: "")) }
            }
        }
        asyncAction {
            profileRepository.flow.collect { outcome ->
                outcome.onSuccess { reduce { copy(profile = it) } }
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
    name = profile?.userName ?: session?.userName ?: ""
)
