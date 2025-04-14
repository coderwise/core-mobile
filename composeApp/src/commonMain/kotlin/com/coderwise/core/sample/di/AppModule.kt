package com.coderwise.core.sample.di

import com.coderwise.core.sample.data.di.sampleDataModule
import com.coderwise.core.sample.ui.di.sampleUiModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(koinConfig: KoinAppDeclaration? = null) {
    startKoin {
        koinConfig?.invoke(this)
        modules(sampleUiModule, sampleDataModule)
    }
}
