package com.coderwise.core.ui.arch

interface NavigationRouter {
    fun navigate(route: Any, addToBackStack: Boolean = true)

    fun navigateUp()
}