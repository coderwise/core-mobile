package com.coderwise.core.permissions.impl

import com.coderwise.core.permissions.Permission

interface PermissionDelegate {
    fun check(): Permission.Status

    fun request()
}