package com.coderwise.core.location.di

import com.coderwise.core.location.LocationService
import com.coderwise.core.location.LocationServiceImpl
import org.koin.dsl.module

actual val coreLocationModule = module {
    single<LocationService> { LocationServiceImpl() }
}