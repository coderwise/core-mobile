package com.coderwise.core.data.di

import com.coderwise.core.data.utils.DataStorePathProducer
import okio.FileSystem
import org.koin.core.module.Module
import org.koin.dsl.module


actual val coreDataModule: Module = module {
    factory { DataStorePathProducer() }
    factory<FileSystem> { FileSystem.SYSTEM }
}