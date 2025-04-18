package com.coderwise.core.auth.data.di

import com.coderwise.core.auth.data.AuthRepositoryImpl
import com.coderwise.core.auth.data.remote.AuthApi
import com.coderwise.core.auth.data.remote.AuthTokenProvider
import com.coderwise.core.auth.domain.AuthRepository
import org.koin.dsl.module

val authDataModule = module {
    factory { AuthApi(get(), get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    single<AuthTokenProvider> {
        object : AuthTokenProvider {
            private var token: String? = null

            override fun get(): String = token ?: throw IllegalStateException("No token")

            override fun reset(token: String) {
                this.token = token
            }
        }
    }
}