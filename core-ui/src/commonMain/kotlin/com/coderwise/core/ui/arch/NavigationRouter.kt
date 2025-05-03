package com.coderwise.core.ui.arch

import androidx.compose.runtime.Stable
import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.Flow


sealed interface NavCommand {
    data class NavigateRoute(
        val route: Any,
        val addToBackStack: Boolean = true
    ) : NavCommand

    data class NavigateString(
        val route: String,
        val addToBackStack: Boolean = true
    ) : NavCommand

    data object NavigateUp : NavCommand
}

@Stable
interface NavigationRouter {
    val flow: Flow<NavCommand>

    suspend fun navigate(route: Any, addToBackStack: Boolean = true)

    suspend fun navigate(route: String, addToBackStack: Boolean = true)

    suspend fun navigateUp()
}

fun Any.routeId(): String = this::class.qualifiedName!!
