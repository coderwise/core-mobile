package com.coderwise.core.auth.domain

import kotlinx.coroutines.flow.Flow

data class User(
    val id: String,
    val name: String,
    val email: String
)

interface UserRepository {

    fun flowById(id: String): Flow<User>

    companion object {
        const val DEFAULT_USER_ID = "DEFAULT_USER_ID"
    }
}
