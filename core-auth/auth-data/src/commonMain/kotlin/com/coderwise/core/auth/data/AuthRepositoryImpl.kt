package com.coderwise.core.auth.data

import com.coderwise.core.auth.data.remote.AuthApi
import com.coderwise.core.auth.data.remote.AuthTokenProvider
import com.coderwise.core.auth.domain.AuthRepository
import com.coderwise.core.auth.domain.AuthResult
import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.domain.arch.tryOutcome

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val tokenProvider: AuthTokenProvider
) : AuthRepository {
    override suspend fun login(
        userName: String,
        password: String
    ): Outcome<AuthResult> = tryOutcome {
        val response = api.login(userName, password)
        tokenProvider.reset(response.token)
        AuthResult(response.token)
    }

    override suspend fun register(
        userName: String,
        password: String
    ): Outcome<Unit> = tryOutcome {
        api.register(userName, password)
    }
}