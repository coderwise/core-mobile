package com.coderwise.core.auth.server

data class User(
    val username: String,
    val password: String,
    val salt: String,
    val id: Int? = null
)
