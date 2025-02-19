package com.coderwise.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coderwise.core.ui.arch.rememberNavRouter
import com.coderwise.core.ui.arch.rememberUiMessenger
import com.coderwise.core.ui.sample.SampleRoute
import com.coderwise.core.ui.sample.SampleScreen
import com.coderwise.core.ui.theme.Core_LibraryTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
                    }
                }
            }
        }
    }
}
