package com.coderwise.core.ui.arch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import kotlinx.coroutines.flow.map
import org.koin.compose.koinInject

class NavControllerProvider {
    private lateinit var navController: NavController

    fun get(): NavController = navController

    fun reset(navController: NavController) {
        this.navController = navController
    }
}


class NavigationRouterImpl(
    private val navControllerProvider: NavControllerProvider,
) : NavigationRouter {
    @Composable
    override fun currentRouteIdAsState(): State<String?> =
        navControllerProvider.get().currentBackStackEntryFlow.map { it.destination.route }
            .collectAsState(null)

    override fun navigate(route: Any, addToBackStack: Boolean) {
        navControllerProvider.get().navigate(route = route) {
            if (!addToBackStack) {
                val popUpRoute = currentRoute()
                if (popUpRoute != null) {
                    popUpTo(popUpRoute) { inclusive = true }
                }
            }
        }
    }

    override fun navigate(route: String, addToBackStack: Boolean) {
        navControllerProvider.get().navigate(route = route) {
            if (!addToBackStack) {
                val popUpRoute = currentRoute()
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
