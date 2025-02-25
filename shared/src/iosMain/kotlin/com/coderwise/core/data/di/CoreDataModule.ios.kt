package com.coderwise.core.data.di

import com.coderwise.core.data.utils.DataStorePathProducer
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
fun dataStorePathProducer() = object : DataStorePathProducer {
    override fun producePath(fileName: String): Path {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return (documentDirectory!!.path + "/$fileName.pb").toPath()
    }
}

actual fun coreDataModule(): Module = module {
    factory { dataStorePathProducer() }
}