package com.coderwise.core.ui.arch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import org.koin.compose.koinInject

class NavControllerProvider {
    private lateinit var navController: NavController

    fun get() = navController

    fun reset(navController: NavController) {
        this.navController = navController
    }
}

class NavigationRouterImpl(
    private val navControllerProvider: NavControllerProvider,
) : NavigationRouter {
    override fun navigate(route: Any, clearBackstack: Boolean) {
        navControllerProvider.get().navigate(route = route) {
            if (clearBackstack) {
                val popUpRoute = currentRoute()
                launchSingleTop = true
                if (popUpRoute != null) {
                    popUpTo(popUpRoute) { inclusive = true }
                }
            }
        }
    }

    override fun navigateUp() {
        navControllerProvider.get().navigateUp()
    }

    private fun currentRoute(): String? =
        navControllerProvider.get().currentBackStackEntry?.destination?.route
}

@Composable
fun rememberNavRouter(
    navController: NavController
): NavigationRouter {
    koinInject<NavControllerProvider>().apply {
        reset(navController)
    }
    val navRouter = koinInject<NavigationRouter>()
    return remember { navRouter }
}
