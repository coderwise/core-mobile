package com.coderwise.core.sample.data.di

import com.coderwise.core.auth.data.di.authDataModule
import com.coderwise.core.auth.data.local.SessionLocalSource
import com.coderwise.core.auth.data.local.SessionLocalSourceDataStoreImpl
import com.coderwise.core.auth.data.local.SessionRecord
import com.coderwise.core.auth.data.remote.AuthUrls
import com.coderwise.core.data.arch.DataStoreRecord
import com.coderwise.core.data.di.coreDataModule
import com.coderwise.core.data.di.createDataStore
import com.coderwise.core.data.remote.UrlProvider
import com.coderwise.core.location.di.coreLocationModule
import com.coderwise.core.permissions.di.corePermissionsModule
import com.coderwise.core.sample.data.profile.ProfileRepository
import com.coderwise.core.sample.data.profile.ProfileRepositoryImpl
import com.coderwise.core.sample.data.profile.local.ProfileLocalSource
import com.coderwise.core.sample.data.profile.local.ProfileRecord
import com.coderwise.core.sample.data.sample.SampleRecord
import com.coderwise.core.sample.data.sample.SampleRepository
import com.coderwise.core.sample.data.sample.SampleRepositoryImpl
import com.coderwise.core.sample.data.sample.local.DataStoreSampleSource
import com.coderwise.core.sample.data.sample.remote.SampleRemoteSource
import com.coderwise.core.sample.data.sample.remote.SampleUrls
import com.coderwise.core.time.di.coreTimeModule
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

object SampleUrlsImpl : SampleUrls, AuthUrls {
    const val BASE_URL = "http://${UrlProvider.emulatorHost}:8080"

    override val login: String = "$BASE_URL/login"
    override val register: String = "$BASE_URL/register"
    override val samples: String = "$BASE_URL/samples"
}

val sampleDataModule = module {
    includes(coreDataModule)
    includes(corePermissionsModule)
    includes(coreLocationModule)
    includes(coreTimeModule)
    includes(authDataModule)

    single { createHttpClient() }
    factory<AuthUrls> { SampleUrlsImpl }

    factory {
        DataStoreSampleSource(
            dataStore = createDataStore(
                defaultValue = DataStoreRecord(
                    List(10) { SampleRecord(it, "sample $it") }
                ),
                serializer = DataStoreRecord.serializer(SampleRecord.serializer())
            )
        )
    }

    factory<SampleUrls> { SampleUrlsImpl }
    factory { SampleRemoteSource(get(), get(), get()) }
    single<SampleRepository> { SampleRepositoryImpl(get()) }

    single<SessionLocalSource> {
        SessionLocalSourceDataStoreImpl(
            createDataStore(
                SessionRecord.DEFAULT, SessionRecord.serializer()
            )
        )
    }

    factory {
        ProfileLocalSource(
            createDataStore(
                ProfileRecord("Local"), ProfileRecord.serializer()
            )
        )
    }
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
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
