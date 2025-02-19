package com.coderwise.core.ui.arch

interface NavigationRouter {
    fun navigate(route: Any, clearBackStack: Boolean = false)

    fun navigateUp()
}