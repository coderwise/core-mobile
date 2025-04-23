package com.coderwise.core.auth.data.di

import com.coderwise.core.auth.data.AuthRepositoryImpl
import com.coderwise.core.auth.data.SessionRepositoryImpl
import com.coderwise.core.auth.data.remote.AuthApi
import com.coderwise.core.auth.domain.AuthRepository
import com.coderwise.core.auth.domain.SessionRepository
import org.koin.dsl.module

val authDataModule = module {

    factory { AuthApi(get(), get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }

    single<SessionRepository> { SessionRepositoryImpl() }
}