package com.coderwise.core.sample.data

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String
)

interface UserRepository {
    suspend fun currentUser(): User?
}