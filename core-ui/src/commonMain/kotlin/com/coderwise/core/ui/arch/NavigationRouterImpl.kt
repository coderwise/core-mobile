package com.coderwise.core.ui.arch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.compose.koinInject


class NavigationRouterImpl() : NavigationRouter {
    private val navCommands = MutableSharedFlow<NavCommand>(replay = 1)
    override val flow: Flow<NavCommand> = navCommands
    override suspend fun navigate(
        route: Any,
        navOptions: (NavOptionsBuilder.() -> Unit)?
    ) {
        navCommands.emit(NavCommand.Navigate(route, navOptions))
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
                is NavCommand.Navigate -> {
                    if (it.navOptions != null)
                        navController.navigate(it.route, it.navOptions)
                    else
                        navController.navigate(it.route)
                }

                is NavCommand.NavigateUp -> navController.navigateUp()
            }
        }
    }
}
