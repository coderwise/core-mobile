package com.coderwise.core.di

import com.coderwise.core.auth.domain.LoginResult
import com.coderwise.core.auth.domain.LoginService
import com.coderwise.core.auth.domain.User
import com.coderwise.core.auth.domain.UserRepository
import com.coderwise.core.auth.ui.di.authUiModule
import com.coderwise.core.data.SampleRecord
import com.coderwise.core.data.SampleRepository
import com.coderwise.core.data.SampleRepositoryImpl
import com.coderwise.core.data.arch.DataStoreRecord
import com.coderwise.core.data.di.coreDataModule
import com.coderwise.core.data.local.DataStoreSampleSource
import com.coderwise.core.data.utils.DataStoreCreator
import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.location.di.coreLocationModule
import com.coderwise.core.permissions.di.corePermissionsModule
import com.coderwise.core.time.di.coreTimeModule
import com.coderwise.core.ui.di.coreUiModule
import com.coderwise.core.ui.location.LocationViewModel
import com.coderwise.core.ui.permissions.PermissionsViewModel
import com.coderwise.core.ui.list.ListViewModel
import com.coderwise.core.ui.list.edit.EditViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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
    includes(coreUiModule)
    includes(coreDataModule)
    includes(corePermissionsModule)
    includes(coreLocationModule)
    includes(coreTimeModule)
    includes(authUiModule)

    single<UserRepository> {
        object : UserRepository {
            override fun flowById(id: String): Flow<User> = flowOf(User("id", "name", "email"))
        }
    }

    single<LoginService> {
        object : LoginService {
            override suspend fun login(
                userName: String,
                password: String
            ): Outcome<LoginResult> = Outcome.Success(LoginResult(true))
        }
    }

    factory {
        DataStoreSampleSource(
            dataStore = DataStoreCreator.create(
                defaultValue = DataStoreRecord(
                    List(10) { SampleRecord(it, "sample $it") }
                ),
                serializer = DataStoreRecord.serializer(SampleRecord.serializer()),
                fileSystem = get(),
                pathProducer = get()
            )
        )
    }

    single<SampleRepository> { SampleRepositoryImpl(get()) }

    viewModel { ListViewModel(get(), get(), get(), get()) }
    viewModel { EditViewModel(get(), get(), get(), get()) }
    viewModel { PermissionsViewModel(get(), get()) }
    viewModel { LocationViewModel(get()) }
}