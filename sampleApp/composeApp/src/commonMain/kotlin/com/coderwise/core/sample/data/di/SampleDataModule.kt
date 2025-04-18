package com.coderwise.core.sample.data.di

import com.coderwise.core.auth.data.di.authDataModule
import com.coderwise.core.auth.domain.User
import com.coderwise.core.auth.domain.UserRepository
import com.coderwise.core.data.arch.DataStoreRecord
import com.coderwise.core.data.di.coreDataModule
import com.coderwise.core.data.remote.UrlProvider
import com.coderwise.core.data.utils.DataStoreCreator
import com.coderwise.core.location.di.coreLocationModule
import com.coderwise.core.permissions.di.corePermissionsModule
import com.coderwise.core.sample.data.SampleRecord
import com.coderwise.core.sample.data.SampleRepository
import com.coderwise.core.sample.data.SampleRepositoryImpl
import com.coderwise.core.sample.data.local.DataStoreSampleSource
import com.coderwise.core.sample.data.remote.SampleRemoteSource
import com.coderwise.core.time.di.coreTimeModule
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val sampleDataModule = module {
    includes(coreDataModule)
    includes(corePermissionsModule)
    includes(coreLocationModule)
    includes(coreTimeModule)
    includes(authDataModule)

    single<UserRepository> {
        object : UserRepository {
            override fun flowById(id: String): Flow<User> = flowOf(User("id", "name", "email"))
        }
    }

    single { createHttpClient() }
    single<UrlProvider> {
        object : UrlProvider {
            override fun get() = "http://${UrlProvider.emulatorHost}:8080"
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

    factory { SampleRemoteSource(get(), get(), get()) }
    single<SampleRepository> { SampleRepositoryImpl(get(), get()) }
}

private fun createHttpClient() = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            explicitNulls = false
            encodeDefaults = false

        })
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
        level = LogLevel.BODY
    }
}
