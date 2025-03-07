package com.coderwise.core.permissions.di

import com.coderwise.core.permissions.Permission
import com.coderwise.core.permissions.PermissionService
import com.coderwise.core.permissions.impl.AndroidPermissionService
import org.koin.core.module.Module
import org.koin.dsl.module


class AndroidPermissionHelper(
) {
    private var showRationale: (Permission) -> Boolean = { false }

    fun setShowRationale(block: (Permission) -> Boolean) {
        showRationale = block
    }

    fun checkShowRationale(permission: Permission): Boolean = showRationale(permission)
}

actual val permissionsModule: Module = module {
    single { AndroidPermissionHelper() }

    single<PermissionService> {
        AndroidPermissionService(
            context = get(),
            shouldShowRationale = get<AndroidPermissionHelper>()::checkShowRationale
        )
    }
}
