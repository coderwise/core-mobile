package com.coderwise.core.di

import com.coderwise.core.data.SampleRepository
import com.coderwise.core.data.SampleRepositoryImpl
import com.coderwise.core.ui.sample.SampleViewModel
import com.coderwise.core.ui.sample.edit.EditViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<SampleRepository> { SampleRepositoryImpl(get()) }
    viewModel { SampleViewModel(get(), get(), get()) }
    viewModel { EditViewModel(get(), get(), get(), get()) }
}