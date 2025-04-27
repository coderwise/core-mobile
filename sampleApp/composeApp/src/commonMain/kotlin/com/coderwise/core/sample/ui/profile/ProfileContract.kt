package com.coderwise.core.sample.ui.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.coderwise.core.auth.domain.Session
import com.coderwise.core.ui.component.scaffold
import kotlinx.serialization.Serializable

@Serializable
object ProfileRoute

fun NavGraphBuilder.profileScreen() {
    composable<ProfileRoute> {
        scaffold {
            showBackNavigation = true
            topBarTitle = "Profile"
            topBarActions = emptyList()
            showBottomBar = false
        }
        ProfileScreen()
    }
}

data class ProfileModelState(
    val session: Session? = null
)

data class ProfileUiState(
    val name: String
)

sealed interface ProfileAction {
    data object Logout : ProfileAction
}