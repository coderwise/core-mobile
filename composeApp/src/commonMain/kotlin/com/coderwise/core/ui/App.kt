package com.coderwise.core.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.coderwise.core.ui.arch.rememberNavRouter
import com.coderwise.core.ui.arch.rememberUiMessenger
import com.coderwise.core.ui.component.CoreTopBar
import com.coderwise.core.ui.permissions.Permissions
import com.coderwise.core.ui.permissions.PermissionsScreen
import com.coderwise.core.ui.sample.SampleScreen
import com.coderwise.core.ui.sample.edit.EditScreen
import com.coderwise.core.ui.theme.Core_LibraryTheme
import com.coderwise.permissions.CAMERA
import com.coderwise.permissions.LOCATION
import com.coderwise.permissions.MICROPHONE
import com.coderwise.permissions.Permission
import com.coderwise.permissions.ProcessPermissionRequestEffect

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

    ProcessPermissionRequestEffect(Permission.LOCATION)
    ProcessPermissionRequestEffect(Permission.CAMERA)
    ProcessPermissionRequestEffect(Permission.MICROPHONE)

    val navController = rememberNavController()
    val navRouter = rememberNavRouter(navController)
    val currentRoute by navRouter.currentRouteAsState()
    val showBackNavigation = currentRoute.hasBackNavigation()

    var currentDestination by rememberSaveable { mutableStateOf("HOME") }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            item(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home"
                    )
                },
                label = { Text("Home") },
                selected = currentDestination == "HOME",
                onClick = {
                    currentDestination = "HOME"
                    navRouter.navigate(Sample)
                }
            )

            item(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Permissions"
                    )
                },
                label = { Text("Permissions") },
                selected = currentDestination == "PERMISSIONS",
                onClick = {
                    currentDestination = "PERMISSIONS"
                    navRouter.navigate(Permissions)
                }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CoreTopBar(
                    title = "Title",
                    showBackNavigation = showBackNavigation,
                    onNavigationClick = navRouter::navigateUp
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

                composable<Permissions> { PermissionsScreen() }
            }
        }
    }
}

private fun String?.hasBackNavigation() = when {
    null == this -> false
    this == Sample::class.qualifiedName -> false
    else -> true
}
