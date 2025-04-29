package com.coderwise.core.auth.data.local

import com.coderwise.core.auth.domain.Session
import kotlinx.coroutines.flow.Flow

interface SessionLocalSource {
    suspend fun setRememberMe(rememberMe: Boolean)
    suspend fun setUserName(userName: String)
    suspend fun setAuthToken(token: String?)

    val flow: Flow<Session>
}