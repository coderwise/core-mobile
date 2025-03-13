package com.coderwise.core.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.coderwise.core.permissions.impl.IosPermissionRouter
import org.koin.compose.koinInject

@Composable
actual fun ProcessPermissionRequestEffect(
    permission: Permission
) {
    val permissionService = koinInject<PermissionService>()

    LaunchedEffect(permission) {
        permissionService.requests.collect {}
    }
}
