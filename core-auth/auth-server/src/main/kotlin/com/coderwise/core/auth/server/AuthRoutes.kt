package com.coderwise.core.auth.server

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.coderwise.core.auth.data.remote.LoginRequest
import com.coderwise.core.auth.data.remote.LoginResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlin.collections.find


data class User(
    val username: String,
    val password: String
)

fun Application.authRouting(
    users: List<User>
) {
}