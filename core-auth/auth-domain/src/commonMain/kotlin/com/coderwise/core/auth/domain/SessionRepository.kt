package com.coderwise.core.auth.domain

import com.coderwise.core.domain.arch.Session
import kotlinx.coroutines.flow.Flow

data class Credentials(
    val userName: String,
    val password: String,
    val rememberMe: Boolean
)

interface SessionRepository : Session {
    val authToken: String

    val credentials: Flow<Credentials>

    fun authenticated(): Flow<Boolean>

    fun setToken(token: String)

    fun setRememberMe(rememberMe: Boolean)

    fun setUserName(userName: String)
}