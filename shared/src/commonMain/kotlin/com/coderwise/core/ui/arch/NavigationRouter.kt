package com.coderwise.core.ui.arch

import androidx.compose.runtime.Immutable

@Immutable
interface NavigationRouter {
    fun navigate(route: Any, addToBackStack: Boolean = true)

    fun navigateUp()
}