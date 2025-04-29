package com.coderwise.core.auth.data

import com.coderwise.core.auth.data.local.SessionLocalSource
import com.coderwise.core.auth.domain.Session
import com.coderwise.core.auth.domain.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onEach

class SessionRepositoryImpl(
    private val sessionLocalSource: SessionLocalSource
) : SessionRepository {
    private val _authenticated = MutableStateFlow(false)

    override suspend fun authToken(): String = session.firstOrNull()?.authToken
        ?: throw IllegalStateException("No token")

    override val session: Flow<Session> = sessionLocalSource.flow.onEach { session ->
        _authenticated.value = session.authToken != null
    }

    override suspend fun setToken(token: String) {
        sessionLocalSource.setAuthToken(token)
    }

    override suspend fun setRememberMe(rememberMe: Boolean) {
        sessionLocalSource.setRememberMe(rememberMe)
    }

    override suspend fun setUserName(userName: String) {
        sessionLocalSource.setUserName(userName)
    }

    override suspend fun clear() {
        sessionLocalSource.setAuthToken(null)
    }
}