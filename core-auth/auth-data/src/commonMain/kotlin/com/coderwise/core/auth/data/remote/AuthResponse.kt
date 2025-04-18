package com.coderwise.core.auth.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String
)
