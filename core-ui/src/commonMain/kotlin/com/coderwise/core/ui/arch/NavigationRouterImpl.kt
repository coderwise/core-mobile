package com.coderwise.core.ui.arch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.compose.koinInject


class NavigationRouterImpl() : NavigationRouter {
    private val navCommands = MutableSharedFlow<NavCommand>(replay = 1)
    override val flow: Flow<NavCommand> = navCommands
    override suspend fun navigate(
        route: Any,
        addToBackStack: Boolean
    ) {
        navCommands.emit(NavCommand.NavigateRoute(route, addToBackStack))
    }

    override suspend fun navigate(
        route: String,
        addToBackStack: Boolean
    ) {
        navCommands.emit(NavCommand.NavigateString(route, addToBackStack))
    }

    override suspend fun navigateUp() {
        navCommands.emit(NavCommand.NavigateUp)
    }
}


@Composable
fun rememberNavRouter(
    navController: NavController
) {
    val navRouter = koinInject<NavigationRouter>()
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(navController, lifecycleOwner) {
        navRouter.flow.collect {
            when (it) {
                is NavCommand.NavigateRoute -> if (!it.addToBackStack)
                    navController.navigate(it.route) {
                        val currentRoute = navController.currentBackStackEntry?.destination?.route
                        popUpTo(currentRoute ?: "") { inclusive = true }
                    }
                else
                    navController.navigate(it.route)

                is NavCommand.NavigateString -> if (!it.addToBackStack)
                    navController.navigate(it.route) {
                        val currentRoute = navController.currentBackStackEntry?.destination?.route
                        popUpTo(currentRoute ?: "") { inclusive = true }
                    }
                else
                    navController.navigate(it.route)

                is NavCommand.NavigateUp -> navController.navigateUp()
            }
        }
    }
}
