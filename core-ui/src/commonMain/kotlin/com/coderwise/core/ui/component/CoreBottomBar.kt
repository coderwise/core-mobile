package com.coderwise.core.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavController
import com.coderwise.core.ui.arch.UiText
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

data class NavItem<T : Any>(
    val route: T,
    val label: UiText,
    val icon: DrawableResource
) {
    fun routeId() = route::class.qualifiedName!!
}

@Composable
fun CoreBottomBar(
    navController: NavController,
    navItems: List<NavItem<Any>>,
    modifier: Modifier = Modifier,
    show: Boolean = true
) {
    val currentRouteId by navController.currentRouteIdAsState()
    AnimatedVisibility(
        visible = show,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        BottomAppBar(
            modifier = modifier,
            content = {
                NavigationBar {
                    navItems.forEach { navItem ->
                        NavigationBarItem(
                            selected = navItem.routeId() == currentRouteId,
                            icon = {
                                Icon(
                                    painter = painterResource(navItem.icon),
                                    contentDescription = navItem.label.asString()
                                )
                            },
                            label = { Text(navItem.label.asString()) },
                            onClick = {
                                navController.navigate(navItem.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        )
                    }
                }
            }
        )
    }
}

@Composable
private fun NavController.currentRouteIdAsState() =
    currentBackStackEntryFlow.map { it.destination.route }
        .collectAsState(null)