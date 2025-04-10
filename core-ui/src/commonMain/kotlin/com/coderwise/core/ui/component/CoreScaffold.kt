package com.coderwise.core.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController

@Composable
fun CoreScaffold(
    navController: NavHostController,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable (PaddingValues) -> Unit
) {
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
                CoreBottomBar(
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