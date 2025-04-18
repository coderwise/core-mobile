package com.coderwise.core.auth.server

data class SaltedHash(
    val hash: String,
    val salt: String
)
