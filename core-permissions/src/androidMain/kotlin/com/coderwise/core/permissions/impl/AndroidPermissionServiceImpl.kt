package com.coderwise.core.permissions.impl

import android.content.Context
import com.coderwise.core.permissions.Permission

class AndroidPermissionService(
    private val context: Context,
    private val shouldShowRationale: (Permission) -> Boolean
) : PermissionServiceImpl() {

    override fun checkPermission(permission: Permission): Permission.Status {
        val androidPermissionId = permission.asAndroidPermissionId()
        val isGranted = isGranted(androidPermissionId)
        val shouldShowRationale = shouldShowRationale(permission)

        val status = when {
            isGranted -> Permission.Status.GRANTED
            !isGranted && !shouldShowRationale -> Permission.Status.DENIED
            else -> Permission.Status.PENDING
        }
        updateStatus(permission, status)
        return status
    }

    private fun isGranted(androidPermissionId: String) =
        context.checkSelfPermission(androidPermissionId) == android.content.pm.PackageManager.PERMISSION_GRANTED
}
