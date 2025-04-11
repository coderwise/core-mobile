package com.coderwise.core.auth.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.coderwise.core.auth.ui.login.LoginRoute
import com.coderwise.core.auth.ui.login.LoginScreen
import com.coderwise.core.auth.ui.recover.RecoverRoute
import com.coderwise.core.auth.ui.register.RegisterRoute
import com.coderwise.core.ui.component.scaffold

fun NavGraphBuilder.authNavigationGraph() {
    composable<LoginRoute> {
        scaffold {
            showTopBar = false
            showBottomBar = false
        }
        LoginScreen()
    }
    composable<RegisterRoute> {

    }
    composable<RecoverRoute> {}
}