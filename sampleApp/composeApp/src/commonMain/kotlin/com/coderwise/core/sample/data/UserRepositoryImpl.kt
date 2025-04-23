package com.coderwise.core.sample.data

class UserRepositoryImpl : UserRepository {
    private val users = listOf(
        User(id = 0, name = "user1", email = "email1", password = "password1"),
        User(id = 0, name = "user2", email = "email2", password = "password2")
    )

    override suspend fun currentUser(): User? {
        return users.firstOrNull()
    }
}