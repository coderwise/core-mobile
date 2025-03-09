package com.coderwise.core.ui.di

import com.coderwise.core.ui.IosSettingsService
import com.coderwise.core.ui.OsSettingsService
import org.koin.dsl.module

actual val osSettingsModule = module {
    factory<OsSettingsService> { IosSettingsService() }
}