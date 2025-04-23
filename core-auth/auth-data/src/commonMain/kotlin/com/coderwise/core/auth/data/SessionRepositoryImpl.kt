package com.coderwise.core.auth.data

import com.coderwise.core.auth.domain.Credentials
import com.coderwise.core.auth.domain.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SessionRepositoryImpl : SessionRepository {
    private val _authenticated = MutableStateFlow(false)
    private val _credentials = MutableStateFlow(Credentials("", "", false))
    private var token: String? = null

    override val authToken: String
        get() = token ?: throw IllegalStateException("No token")

    override val credentials: Flow<Credentials> = _credentials.asStateFlow()

    override fun authenticated(): Flow<Boolean> = _authenticated.asStateFlow()

    override fun setToken(token: String) {
        this.token = token
        _authenticated.value = true
    }

    override fun setRememberMe(rememberMe: Boolean) {
        _credentials.update { it.copy(rememberMe = rememberMe) }
    }

    override fun setUserName(userName: String) {
        _credentials.update { it.copy(userName = userName) }
    }

    override fun clear() {
        token = null
        _authenticated.value = false
    }
}