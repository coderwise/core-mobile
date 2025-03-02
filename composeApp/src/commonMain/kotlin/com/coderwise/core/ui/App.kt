package com.coderwise.core.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.coderwise.core.ui.arch.rememberNavRouter
import com.coderwise.core.ui.arch.rememberUiMessenger
import com.coderwise.core.ui.component.CoreTopBar
import com.coderwise.core.ui.sample.SampleScreen
import com.coderwise.core.ui.sample.edit.EditScreen
import com.coderwise.core.ui.theme.Core_LibraryTheme

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

    val navController = rememberNavController()
    val navRouter = rememberNavRouter(navController)
    val currentRoute by navRouter.currentRouteAsState()
    val showBackNavigation = currentRoute.hasBackNavigation()

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
        }
    }
}

private fun String?.hasBackNavigation() = when {
    null == this -> false
    this == Sample::class.qualifiedName -> false
    else -> true
}
