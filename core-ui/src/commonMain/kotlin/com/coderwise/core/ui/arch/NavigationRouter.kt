package com.coderwise.core.ui.arch

import androidx.compose.runtime.Stable
import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.Flow


sealed interface NavCommand {
    data class Navigate(
        val route: Any,
        val navOptions: (NavOptionsBuilder.() -> Unit)? = null
    ) : NavCommand

    data object NavigateUp : NavCommand
}

@Stable
interface NavigationRouter {
    val flow: Flow<NavCommand>

    suspend fun navigate(route: Any, navOptions: (NavOptionsBuilder.() -> Unit)? = null)

    suspend fun navigateUp()
}

fun Any.routeId(): String = this::class.qualifiedName!!
