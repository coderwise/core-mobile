package com.coderwise.permissions

import com.coderwise.permissions.Permission.Status

interface Permission {
    enum class Status {
        GRANTED,
        NOT_GRANTED,
        NOT_GRANTED_PERMANENTLY
    }

    companion object
}

val Status.isGranted get() = this == Status.GRANTED
