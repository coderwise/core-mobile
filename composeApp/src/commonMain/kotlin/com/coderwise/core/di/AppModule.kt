package com.coderwise.core.di

import com.coderwise.core.data.SampleRecord
import com.coderwise.core.data.SampleRepository
import com.coderwise.core.data.SampleRepositoryImpl
import com.coderwise.core.data.arch.DataStoreRecord
import com.coderwise.core.data.di.coreDataModule
import com.coderwise.core.data.local.DataStoreSampleSource
import com.coderwise.core.data.utils.DataStoreCreator
import com.coderwise.core.location.di.coreLocationModule
import com.coderwise.core.permissions.di.corePermissionsModule
import com.coderwise.core.ui.di.coreUiModule
import com.coderwise.core.ui.location.LocationViewModel
import com.coderwise.core.ui.permissions.PermissionsViewModel
import com.coderwise.core.ui.sample.SampleViewModel
import com.coderwise.core.ui.sample.edit.EditViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(koinConfig: KoinAppDeclaration? = null) {
    startKoin {
        koinConfig?.invoke(this)
        modules(appModule, coreUiModule)
    }
}

val appModule = module {
    includes(coreDataModule)
    includes(coreUiModule)
    includes(corePermissionsModule)
    includes(coreLocationModule)

    factory {
        DataStoreSampleSource(
            dataStore = DataStoreCreator.create(
                defaultValue = DataStoreRecord<SampleRecord>(
                    List(10) { SampleRecord(it.toString(), "sample $it") }
                ),
                serializer = DataStoreRecord.serializer(SampleRecord.serializer()),
                fileSystem = get(),
                pathProducer = get()
            )
        )
    }

    single<SampleRepository> { SampleRepositoryImpl(get()) }

    viewModel { SampleViewModel(get(), get(), get()) }
    viewModel { EditViewModel(get(), get(), get(), get()) }
    viewModel { PermissionsViewModel(get(), get()) }
    viewModel { LocationViewModel(get()) }
}