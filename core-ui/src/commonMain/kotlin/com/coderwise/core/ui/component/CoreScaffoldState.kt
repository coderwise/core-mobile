package com.coderwise.core.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


class ScaffoldState {
    var showTopBar by mutableStateOf(false)
    var showBackNavigation by mutableStateOf(false)
    var topBarTitle by mutableStateOf("")
    var topBarActions by mutableStateOf(emptyList<TopBarAction>())

    var bottomBarNavItems by mutableStateOf(emptyList<NavItem<out Any>>())
    var showBottomBar by mutableStateOf(false)
}

val LocalScaffoldState = compositionLocalOf { ScaffoldState() }

@Composable
fun scaffold(stateBuilder: ScaffoldState.() -> Unit) {
    LocalScaffoldState.current.stateBuilder()
}