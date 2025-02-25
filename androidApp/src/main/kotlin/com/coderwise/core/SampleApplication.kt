package com.coderwise.core

import android.app.Application
import com.coderwise.core.di.appModule
import com.coderwise.core.di.initKoin
import com.coderwise.core.ui.di.coreUiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@SampleApplication)
        }
    }
}