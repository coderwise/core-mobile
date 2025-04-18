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
import java.util.Date
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun Application.configureAuth(
    config: JWTConfig
) {
    install(Authentication) {
        jwt {
            realm = config.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.secret))
                    .withAudience(config.audience)
                    .withIssuer(config.issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(config.audience))
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
            val loginRequest = call.receive<LoginRequest>()
//                users.find { it.username == request.username && it.password == request.password }

            val token = JWT.create()
                .withAudience(config.audience)
                .withIssuer(config.issuer)
                .withClaim("username", loginRequest.username)
                .withExpiresAt(
                    Date(
                        Clock.System.now().plus(config.expiration).toEpochMilliseconds()
                    )
                )
                .sign(Algorithm.HMAC256(config.secret))

            call.respond(HttpStatusCode.OK, LoginResponse(token))
        }
    }
}