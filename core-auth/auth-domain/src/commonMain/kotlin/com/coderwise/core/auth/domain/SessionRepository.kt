package com.coderwise.core.auth.domain

import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    val session: Flow<Session>

    suspend fun authToken(): String

    suspend fun setToken(token: String)

    suspend fun setRememberMe(rememberMe: Boolean)

    suspend fun setUserName(userName: String)

    suspend fun clear()
}