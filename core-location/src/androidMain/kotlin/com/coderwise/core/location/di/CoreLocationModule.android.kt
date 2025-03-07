package com.coderwise.core.location.di

import android.content.Context
import android.location.LocationManager
import com.coderwise.core.location.LocationService
import com.coderwise.core.location.LocationServiceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val coreLocationModule = module {
    single { androidContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    single<LocationService> { LocationServiceImpl(get()) }
}