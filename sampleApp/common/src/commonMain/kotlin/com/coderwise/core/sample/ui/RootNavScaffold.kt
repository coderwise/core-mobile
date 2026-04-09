package com.coderwise.core.sample.ui

import androidx.compose.foundation.layout.padding
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
import core_library.sampleapp.common.generated.resources.Res
import core_library.sampleapp.common.generated.resources.home_24px
import core_library.sampleapp.common.generated.resources.location_on_24px
import core_library.sampleapp.common.generated.resources.outline_settings_24
import org.jetbrains.compose.resources.painterResource

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
                painter = painterResource(navItem.findImageVector()),
                contentDescription = ""
            )
        },
        label = { Text("") },
        selected = currentDestination == navItem,
        onClick = onClick
    )
}

private fun Any.findImageVector() = when (this) {
    is ListRoute -> Res.drawable.home_24px
    is PermissionsRoute -> Res.drawable.outline_settings_24
    is LocationRoute -> Res.drawable.location_on_24px
    else -> Res.drawable.home_24px
}
