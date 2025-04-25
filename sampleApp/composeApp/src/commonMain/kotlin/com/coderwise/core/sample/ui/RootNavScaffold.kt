package com.coderwise.core.sample.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.coderwise.core.sample.ui.list.ListRoute
import com.coderwise.core.sample.ui.location.LocationRoute
import com.coderwise.core.sample.ui.permissions.PermissionsRoute
import com.coderwise.core.ui.component.NavItem

@Composable
fun RootNavScaffold(
    navController: NavHostController,
    bottomNavItems: List<NavItem<Any>>,
    modifier: Modifier = Modifier,
    navGraphBuilder: NavGraphBuilder.() -> Unit = {}
) {
//    var currentDestination by remember { mutableStateOf(Route(ListRoute)) }
//    val scaffoldState = rememberNavigationSuiteScaffoldState()
//    val scope = rememberCoroutineScope()
//    scope.launch {
//        if (currentDestination.showBottomBar) {
//            scaffoldState.show()
//        } else {
//            scaffoldState.hide()
//        }
//    }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            bottomNavItems.forEach { navItem ->
//                routeItem(navItem, currentDestination) {
//                    currentDestination = navItem
//                    navController.navigate(navItem)
//                }
            }
        },
        modifier = modifier
    ) {
        Scaffold(
            topBar = {}
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = ListRoute,
                modifier = Modifier.padding(innerPadding)
            ) {
                navGraphBuilder()
            }
        }
    }
}

fun <T : Any> NavigationSuiteScope.routeItem(
    navItem: NavItem<T>,
    currentDestination: NavItem<Any>,
    onClick: () -> Unit
) {
    item(
        icon = {
            Icon(
                imageVector = navItem.findImageVector(),
                contentDescription = ""
            )
        },
        label = { Text("") },
        selected = currentDestination == navItem,
        onClick = onClick
    )
}

private fun Any.findImageVector() = when (this) {
    is ListRoute -> Icons.Default.Home
    is PermissionsRoute -> Icons.Default.Settings
    is LocationRoute -> Icons.Default.LocationOn
    else -> Icons.Default.Home
}
