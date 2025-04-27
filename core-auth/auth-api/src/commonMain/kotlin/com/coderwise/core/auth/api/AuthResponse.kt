package com.coderwise.core.auth.api

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String
)