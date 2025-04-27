package com.coderwise.core.auth.data.remote

import androidx.datastore.core.DataStore
import com.coderwise.core.auth.domain.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@Serializable
data class SessionRecord(
    val name: String,
    val rememberMe: Boolean,
    val token: String?
) {
    companion object {
        val DEFAULT = SessionRecord(
            name = "",
            rememberMe = false,
            token = null
        )
    }
}

class SessionLocalSourceDataStoreImpl(
    private val dataStore: DataStore<SessionRecord>
) : SessionLocalSource {
    override val flow: Flow<Session> =
        dataStore.data.map { userRecord -> userRecord.asDomainModel() }

    override suspend fun setRememberMe(rememberMe: Boolean) {
        dataStore.updateData {
            it.copy(rememberMe = rememberMe)
        }
    }

    override suspend fun setUserName(userName: String) {
        dataStore.updateData {
            it.copy(name = userName)
        }
    }

    override suspend fun setAuthToken(token: String?) {
        dataStore.updateData {
            it.copy(token = token)
        }
    }
}

private fun SessionRecord.asDomainModel() = Session(
    userName = name,
    rememberMe = rememberMe,
    authToken = token
)
