package com.coderwise.core.permissions

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow

interface PermissionService {
    val requests: Flow<Permission>

    fun statusFlow(permission: Permission): Flow<Permission.Status>

    fun checkPermission(permission: Permission): Permission.Status

    suspend fun requestPermission(permission: Permission)
}

@Composable
expect fun ProcessPermissionRequestEffect(permission: Permission)