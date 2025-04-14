package com.coderwise.core.sample.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.coderwise.core.auth.ui.authNavigationGraph
import com.coderwise.core.ui.arch.UiText
import com.coderwise.core.ui.arch.rememberNavRouter
import com.coderwise.core.ui.component.CoreScaffold
import com.coderwise.core.ui.component.NavItem
import com.coderwise.core.ui.component.rememberScaffoldState
import com.coderwise.core.sample.ui.theme.Core_LibraryTheme

@Composable
fun App() {
    Core_LibraryTheme {
        RootUi()
    }
}

@Composable
private fun RootUi() {
    val navController = rememberNavController()
    rememberNavRouter(navController)

    val scaffoldState = rememberScaffoldState().apply {
        showTopBar = true
        showBackNavigation = false
        topBarTitle = "Home"
        showBottomBar = true
        bottomBarNavItems = listOf(
            NavItem(ListRoute, UiText.Plain("Home"), Icons.Default.Home),
            NavItem(PermissionsRoute, UiText.Plain("Permissions"), Icons.Default.Settings),
            NavItem(LocationRoute, UiText.Plain("Location"), Icons.Default.LocationOn)
        )
    }

    CoreScaffold(
        navController = navController,
        scaffoldState = scaffoldState,
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ListRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            sampleNavigationGraph()
            authNavigationGraph()
        }
    }
}
