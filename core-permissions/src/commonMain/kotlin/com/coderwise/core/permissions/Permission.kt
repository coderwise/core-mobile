package com.coderwise.core.permissions

import com.coderwise.core.permissions.Permission.Status

interface Permission {
    enum class Status {
        GRANTED,
        PENDING,
        DENIED
    }

    companion object
}

val Status.isGranted get() = this == Status.GRANTED
