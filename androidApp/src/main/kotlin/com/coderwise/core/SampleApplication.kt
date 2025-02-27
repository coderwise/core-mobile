package com.coderwise.core

import android.app.Application
import com.coderwise.core.di.initKoin
import org.koin.android.ext.koin.androidContext

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@SampleApplication)
        }
    }
}