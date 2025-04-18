package com.coderwise.core.sample.server.data

import com.coderwise.core.auth.server.User

class UserDataSource {
    private val users = mutableListOf<User>()

    suspend fun getUserByUsername(username: String): User? = users.find { it.username == username }

    suspend fun insertUser(user: User): Boolean {
        users.add(user)
        return true
    }
}