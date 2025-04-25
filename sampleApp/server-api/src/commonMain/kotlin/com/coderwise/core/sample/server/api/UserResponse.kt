package com.coderwise.core.sample.server.api

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val username: String
)