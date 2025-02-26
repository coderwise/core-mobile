package com.coderwise.core.ui.arch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State

@Immutable
interface NavigationRouter {
    @Composable
    fun currentRouteAsState(): State<String?>

    fun navigate(route: Any, addToBackStack: Boolean = true)

    fun navigateUp()
}