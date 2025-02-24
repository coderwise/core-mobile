package com.coderwise.core.ui.di

import com.coderwise.core.ui.arch.NavControllerProvider
import com.coderwise.core.ui.arch.NavigationRouter
import com.coderwise.core.ui.arch.NavigationRouterImpl
import com.coderwise.core.ui.arch.SnackbarHostStateProvider
import com.coderwise.core.ui.arch.UiMessenger
import com.coderwise.core.ui.arch.UiMessengerImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val coreUiModule = module {
    single { SnackbarHostStateProvider() }
    single<UiMessenger> { UiMessengerImpl(get(), CoroutineScope(Dispatchers.Main)) }

    single { NavControllerProvider() }
    single<NavigationRouter> { NavigationRouterImpl(get()) }
}