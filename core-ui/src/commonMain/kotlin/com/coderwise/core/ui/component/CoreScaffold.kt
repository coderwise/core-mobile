package com.coderwise.core.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.coderwise.core.ui.arch.rememberNavRouter
import com.coderwise.core.ui.arch.rememberUiMessenger

@Composable
fun CoreScaffold(
    navController: NavHostController = rememberNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    bottomBar: (@Composable () -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    rememberUiMessenger(snackbarHostState)
    rememberNavRouter(navController)

    CompositionLocalProvider(LocalScaffoldState provides scaffoldState) {
        Scaffold(
            topBar = {
                CoreTopBar(
                    title = scaffoldState.topBarTitle,
                    show = scaffoldState.showTopBar,
                    showBackNavigation = scaffoldState.showBackNavigation,
                    actions = scaffoldState.topBarActions,
                    onNavigationClick = { navController.navigateUp() }
                )
            },
            snackbarHost = { CoreSnackbarHost(snackbarHostState) },
            bottomBar = {
                bottomBar?.invoke() ?: CoreBottomBar(
                    navController = navController,
                    navItems = scaffoldState.bottomBarNavItems,
                    show = scaffoldState.showBottomBar
                )
            }
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}

@Composable
fun rememberScaffoldState(): ScaffoldState {
    return remember { ScaffoldState() }
}