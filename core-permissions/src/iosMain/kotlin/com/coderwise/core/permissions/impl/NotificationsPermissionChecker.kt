package com.coderwise.core.permissions.impl

import com.coderwise.core.permissions.Permission

class NotificationsPermissionChecker(
    onStatusUpdated: (Permission, Permission.Status) -> Unit
) : PermissionChecker {
    override fun check(): Permission.Status {
        return Permission.Status.GRANTED
    }

    override fun request() {

    }
}
