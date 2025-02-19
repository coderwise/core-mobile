package com.coderwise.core.ui.di

import com.coderwise.core.ui.arch.NavControllerProvider
import com.coderwise.core.ui.arch.NavigationRouter
import com.coderwise.core.ui.arch.NavigationRouterImpl
import org.koin.dsl.module

val coreUiModule = module {
    single { NavControllerProvider() }
    single<NavigationRouter> { NavigationRouterImpl(get()) }
}