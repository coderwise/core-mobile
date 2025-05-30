package com.coderwise.core.sample

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

interface Functor<out A> {
    fun <B> map(block: (A) -> B): Functor<B>
}