package com.coderwise.core.permissions.impl

import com.coderwise.core.permissions.Permission

interface PermissionChecker {
    fun check(): Permission.Status

    fun request()
}