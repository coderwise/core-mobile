package com.coderwise.core.permissions.di

import com.coderwise.core.permissions.impl.IosPermissionService
import com.coderwise.core.permissions.PermissionService
import org.koin.core.module.Module
import org.koin.dsl.module

actual val corePermissionsModule: Module = module {
    single<PermissionService> { IosPermissionService() }
}
