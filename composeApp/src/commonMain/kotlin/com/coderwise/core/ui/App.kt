package com.coderwise.core.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.coderwise.core.ui.arch.rememberNavRouter
import com.coderwise.core.ui.arch.rememberUiMessenger
import com.coderwise.core.ui.sample.SampleRoute
import com.coderwise.core.ui.sample.SampleScreen
import com.coderwise.core.ui.sample.edit.EditRoute
import com.coderwise.core.ui.sample.edit.EditScreen
import com.coderwise.core.ui.theme.Core_LibraryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    Core_LibraryTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        rememberUiMessenger(snackbarHostState)

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Title") }
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->
            val navController = rememberNavController()
            rememberNavRouter(navController)

            NavHost(
                navController = navController,
                startDestination = SampleRoute,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable<SampleRoute> { SampleScreen() }
                composable<EditRoute> { EditScreen(args = it.toRoute()) }
            }
        }
    }
}