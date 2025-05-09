package com.coderwise.core.sample.server.data.di

import com.coderwise.core.sample.server.data.UserDataSource
import org.koin.dsl.module

val serverDataModule = module {
    single { UserDataSource() }
}
