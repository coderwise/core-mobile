package com.coderwise.core.auth.ui.di

import com.coderwise.core.auth.ui.login.LoginViewModel
import com.coderwise.core.auth.ui.register.RegisterViewModel
import com.coderwise.core.ui.di.coreUiModule
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authUiModule = module {
    includes(coreUiModule)

    viewModel { LoginViewModel(get(), get(), get(), get()) }
    viewModel { RegisterViewModel() }
}