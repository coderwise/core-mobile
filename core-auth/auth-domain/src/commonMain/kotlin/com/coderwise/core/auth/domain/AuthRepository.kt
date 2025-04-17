package com.coderwise.core.auth.domain

import com.coderwise.core.domain.arch.Outcome

data class AuthResult(
    val token: String
)

interface AuthRepository {
    suspend fun login(userName: String, password: String): Outcome<AuthResult>
    suspend fun register(userName: String, password: String): Outcome<AuthResult>
}