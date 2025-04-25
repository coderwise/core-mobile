package com.coderwise.core.ui.arch

import androidx.compose.runtime.Immutable
import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.Flow


data class NavCommand(
    val route: Any,
    val navOptions: NavOptions? = null
)

@Immutable
interface NavigationRouter {
    val flow: Flow<NavCommand>

//    @Composable
//    fun currentRouteIdAsState(): State<String?>

    fun navigate(route: Any, addToBackStack: Boolean = true)

    fun navigate(route: String, addToBackStack: Boolean = true)

    fun navigate(route: Any, navOptions: NavOptions)

    fun navigateUp()
}

fun Any.routeId(): String = this::class.qualifiedName!!
