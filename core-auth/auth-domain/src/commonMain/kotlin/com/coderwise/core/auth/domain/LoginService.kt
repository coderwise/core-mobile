package com.coderwise.core.auth.domain

import com.coderwise.core.domain.arch.Outcome

data class LoginResult(
    val success: Boolean
)

interface LoginService {
    suspend fun login(userName: String, password: String): Outcome<LoginResult>
}