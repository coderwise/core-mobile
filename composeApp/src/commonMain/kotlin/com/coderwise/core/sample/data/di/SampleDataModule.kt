package com.coderwise.core.sample.data.di

import com.coderwise.core.auth.domain.LoginResult
import com.coderwise.core.auth.domain.LoginService
import com.coderwise.core.auth.domain.User
import com.coderwise.core.auth.domain.UserRepository
import com.coderwise.core.sample.data.SampleRecord
import com.coderwise.core.sample.data.SampleRepositoryImpl
import com.coderwise.core.data.arch.DataStoreRecord
import com.coderwise.core.data.di.coreDataModule
import com.coderwise.core.sample.data.local.DataStoreSampleSource
import com.coderwise.core.data.utils.DataStoreCreator
import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.location.di.coreLocationModule
import com.coderwise.core.permissions.di.corePermissionsModule
import com.coderwise.core.sample.data.SampleRepository
import com.coderwise.core.time.di.coreTimeModule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.dsl.module

val sampleDataModule = module {
    includes(coreDataModule)
    includes(corePermissionsModule)
    includes(coreLocationModule)
    includes(coreTimeModule)

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
            ): Outcome<LoginResult> = if (userName == "test" && password == "test") {
                Outcome.Success(LoginResult(""))
            } else {
                Outcome.Error(Exception("Invalid credentials"))
            }
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
}