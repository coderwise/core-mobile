package com.coderwise.core.di

import com.coderwise.core.data.di.sampleDataModule
import com.coderwise.core.ui.di.sampleUiModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(koinConfig: KoinAppDeclaration? = null) {
    startKoin {
        koinConfig?.invoke(this)
        modules(sampleUiModule, sampleDataModule)
    }
}
