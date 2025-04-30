package com.coderwise.core.data.di

import com.coderwise.core.data.utils.DataStoreCreator
import kotlinx.serialization.KSerializer
import org.koin.core.module.Module
import org.koin.core.scope.Scope

expect val coreDataModule: Module

inline fun <reified T> Scope.createDataStore(
    defaultValue: T,
    serializer: KSerializer<T>
) = DataStoreCreator.create(
    defaultValue = defaultValue,
    serializer = serializer,
    fileSystem = get(),
    pathProducer = get()
)