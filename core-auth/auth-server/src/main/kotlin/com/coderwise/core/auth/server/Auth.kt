package com.coderwise.core.auth.server

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.coderwise.core.auth.data.remote.LoginRequest
import com.coderwise.core.auth.data.remote.LoginResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureAuth(
    authConfig: AuthConfig
) {
    install(Authentication) {
        jwt {
            realm = authConfig.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(authConfig.jwtSecret))
                    .withAudience(authConfig.jwtAudience)
                    .withIssuer(authConfig.jwtIssuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(authConfig.jwtAudience))
                    JWTPrincipal(credential.payload)
                else
                    null
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }

    routing {
        post("/login") {
            val request = call.receive<LoginRequest>()
            val user = request.username
//                users.find { it.username == request.username && it.password == request.password }

            val token = JWT.create()
                .withAudience("audience")
                .withIssuer("issuer")
                .withClaim("username", user)
                .sign(Algorithm.HMAC256("secret"))

            call.respond(HttpStatusCode.OK, LoginResponse(token))
        }
    }
}