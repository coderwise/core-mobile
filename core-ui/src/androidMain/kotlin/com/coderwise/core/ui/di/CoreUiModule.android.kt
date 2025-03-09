package com.coderwise.core.ui.di

import com.coderwise.core.ui.AndroidSettingsService
import com.coderwise.core.ui.OsSettingsService
import org.koin.dsl.module

actual val osSettingsModule = module {
    factory<OsSettingsService> { AndroidSettingsService(get()) }
}