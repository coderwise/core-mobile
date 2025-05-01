package com.coderwise.core.auth.data.local

import com.coderwise.core.auth.domain.Session
import com.coderwise.core.data.utils.OneMemCache
import kotlinx.coroutines.flow.Flow

class SessionLocalSourceInMemoryImpl : SessionLocalSource {
    private val cache = OneMemCache(Session.DEFAULT)

    override val flow: Flow<Session> = cache.flow

    override suspend fun setRememberMe(rememberMe: Boolean) {
        cache.update {
            it.copy(rememberMe = rememberMe)
        }
    }

    override suspend fun setUserName(userName: String) {
        cache.update {
            it.copy(userName = userName)
        }
    }

    override suspend fun setAuthToken(token: String?) {
        cache.update {
            it.copy(authToken = token)
        }
    }
}