package com.coderwise.core.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.coderwise.core.data.SampleRepository
import com.coderwise.core.ui.arch.rememberNavRouter
import com.coderwise.core.ui.arch.rememberUiMessenger
import com.coderwise.core.ui.component.CoreTopBar
import com.coderwise.core.ui.location.LocationRoute
import com.coderwise.core.ui.location.LocationScreen
import com.coderwise.core.ui.permissions.PermissionsRoute
import com.coderwise.core.ui.permissions.PermissionsScreen
import com.coderwise.core.ui.sample.SampleScreen
import com.coderwise.core.ui.sample.edit.EditScreen
import com.coderwise.core.ui.theme.Core_LibraryTheme
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun App() {
    Core_LibraryTheme {
        RootUi()
    }
}

@Composable
private fun RootUi() {
    val snackbarHostState = remember { SnackbarHostState() }
    rememberUiMessenger(snackbarHostState)
    val sampleRepository = koinInject<SampleRepository>()

    val navController = rememberNavController()
    val navRouter = rememberNavRouter(navController)
    val currentRoute by navRouter.currentRouteAsState()
    val showBackNavigation = currentRoute.hasBackNavigation()

    var currentDestination by rememberSaveable { mutableStateOf(NavItems.SAMPLE.routeId()) }
    val scope = rememberCoroutineScope()

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            NavItems.entries.forEach {
                val navItem = it
                item(
                    icon = {
                        Icon(
                            imageVector = navItem.imageVector,
                            contentDescription = navItem.name
                        )
                    },
                    label = { Text(navItem.name) },
                    selected = currentDestination == navItem.routeId(),
                    onClick = {
                        currentDestination = navItem.routeId()
                        navRouter.navigate(navItem.route)
                    }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CoreTopBar(
                    title = "Title",
                    showBackNavigation = showBackNavigation,
                    onNavigationClick = navRouter::navigateUp,
                    actions = listOf(
                        {
                            IconButton(
                                onClick = {
                                    scope.launch { sampleRepository.reset() }
                                }
                            ) {
                                Icon(Icons.Default.Delete, null)
                            }
                        }
                    )
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = Sample,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable<Sample> { SampleScreen() }
                composable<Edit> { EditScreen(args = it.toRoute<Edit>()) }

                composable<PermissionsRoute> { PermissionsScreen() }
                composable<LocationRoute> { LocationScreen() }
            }
        }
    }
}

private fun String?.hasBackNavigation() = when {
    null == this -> false
    this == Sample::class.qualifiedName -> false
    this == PermissionsRoute::class.qualifiedName -> false
    this == LocationRoute::class.qualifiedName -> false
    else -> true
}
