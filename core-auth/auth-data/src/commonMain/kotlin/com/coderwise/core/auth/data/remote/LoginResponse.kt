package com.coderwise.core.auth.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String
)
