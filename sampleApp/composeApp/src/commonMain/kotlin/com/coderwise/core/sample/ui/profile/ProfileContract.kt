package com.coderwise.core.sample.ui.profile

import com.coderwise.core.sample.data.User
import kotlinx.serialization.Serializable

@Serializable
object ProfileRoute

data class ProfileModelState(
    val user: User? = null
)

data class ProfileUiState(
    val name: String
)

sealed class ProfileAction {

}