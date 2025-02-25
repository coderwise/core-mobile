package com.coderwise.core.data.di

import android.content.Context
import androidx.datastore.dataStoreFile
import com.coderwise.core.data.utils.DataStorePathProducer
import okio.Path
import okio.Path.Companion.toOkioPath
import org.koin.core.module.Module
import org.koin.dsl.module

class AndroidDataStorePathProducer(
    private val context: Context
) : DataStorePathProducer {
    override fun producePath(fileName: String): Path {
        return context.dataStoreFile("$fileName.pb").toOkioPath()
    }
}

actual fun coreDataModule(): Module = module {
    factory<DataStorePathProducer> { AndroidDataStorePathProducer(get()) }
}