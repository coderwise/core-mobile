package com.coderwise.core.permissions.impl

import com.coderwise.core.permissions.Permission
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class IosPermissionService : PermissionServiceImpl() {
    private val permissionRouter = IosPermissionRouter { permission, status ->
        updateStatus(permission, status)
    }

    override val requests: Flow<Permission> = super.requests.onEach {
        permissionRouter.request(it)
    }

    override fun checkPermission(permission: Permission): Permission.Status {
        val status = permissionRouter.check(permission)
        return status
    }
}
