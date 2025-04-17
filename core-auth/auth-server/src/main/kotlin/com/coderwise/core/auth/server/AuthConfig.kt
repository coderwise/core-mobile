package com.coderwise.core.auth.server

data class AuthConfig(
    val realm: String,
    val jwtAudience: String,
    val jwtIssuer: String,
    val jwtSecret: String,
    val jwtExpiration: Long
)
