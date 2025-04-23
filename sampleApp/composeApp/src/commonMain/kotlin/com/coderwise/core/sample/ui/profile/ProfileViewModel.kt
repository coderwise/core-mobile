package com.coderwise.core.sample.ui.profile

import com.coderwise.core.sample.data.UserRepository
import com.coderwise.core.ui.arch.BaseViewModel

class ProfileViewModel(
    private val userRepository: UserRepository
) : BaseViewModel<ProfileModelState, ProfileUiState>(
    initialState = ProfileModelState(),
    mapper = { it.asUiState() }
) {
    init {
        asyncAction {
            userRepository.currentUser()?.let {
                reduce { copy(user = it) }
            }
        }
    }
}

private fun ProfileModelState.asUiState() = ProfileUiState(
    name = user?.name ?: ""
)
