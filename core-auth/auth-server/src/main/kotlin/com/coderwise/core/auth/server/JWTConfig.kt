package com.coderwise.core.auth.server

import kotlin.time.Duration

data class JWTConfig(
    val realm: String,
    val audience: String,
    val issuer: String,
    val secret: String,
    val expiration: Duration
)
