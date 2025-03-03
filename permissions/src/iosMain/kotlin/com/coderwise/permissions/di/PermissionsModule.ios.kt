package com.coderwise.permissions.di

import com.coderwise.permissions.PermissionService
import com.coderwise.permissions.impl.IosPermissionService
import org.koin.core.module.Module
import org.koin.dsl.module

actual val permissionsModule: Module = module {
    single<PermissionService> { IosPermissionService() }
}
