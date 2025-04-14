package com.coderwise.core.ui.di

import com.coderwise.core.auth.ui.di.authUiModule
import com.coderwise.core.ui.list.ListViewModel
import com.coderwise.core.ui.list.edit.EditViewModel
import com.coderwise.core.ui.location.LocationViewModel
import com.coderwise.core.ui.permissions.PermissionsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val sampleUiModule = module {
    includes(coreUiModule)
    includes(authUiModule)

    viewModel { ListViewModel(get(), get(), get(), get()) }
    viewModel { EditViewModel(get(), get(), get(), get()) }
    viewModel { PermissionsViewModel(get(), get()) }
    viewModel { LocationViewModel(get()) }
}