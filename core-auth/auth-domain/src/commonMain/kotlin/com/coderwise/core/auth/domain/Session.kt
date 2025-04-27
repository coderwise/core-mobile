package com.coderwise.core.auth.domain

data class Session(
    val userName: String? = null,
    val rememberMe: Boolean = false,
    val authToken: String? = null
)