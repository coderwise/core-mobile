package com.coderwise.core.permissions.impl

import com.coderwise.core.permissions.Permission
import com.coderwise.core.permissions.PermissionService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlin.collections.plus

abstract class PermissionServiceImpl : PermissionService {
    private val _statuses = MutableStateFlow(emptyMap<Permission, Permission.Status>())
    private val _requests = MutableSharedFlow<Permission>()

    override val requests: Flow<Permission> = _requests

    override fun statusFlow(permission: Permission): Flow<Permission.Status> = _statuses
        .onStart {
            updateStatus(permission, checkPermission(permission))
        }
        .filter {
            it[permission] != null
        }.map {
            it.getValue(permission)
        }

    override suspend fun requestPermission(permission: Permission) {
        _requests.emit(permission)
    }

    protected fun updateStatus(permission: Permission, status: Permission.Status) {
        _statuses.update {
            it + mapOf(permission to status)
        }
    }
}