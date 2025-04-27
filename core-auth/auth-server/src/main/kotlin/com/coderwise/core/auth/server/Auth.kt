package com.coderwise.core.auth.server

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.coderwise.core.auth.api.AuthRequest
import com.coderwise.core.auth.api.AuthResponse
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
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun Application.configureAuth(
    tokenConfig: JwtConfig,
    findUser: suspend (String) -> User?,
    createUser: suspend (User) -> Boolean,
    tokenService: JwtService = JwtService(),
    hashingService: HashingService = HashingService()
) {
    install(Authentication) {
        jwt {
            realm = tokenConfig.realm
            verifier(
                JWT.require(Algorithm.HMAC256(tokenConfig.secret))
                    .withAudience(tokenConfig.audience).withIssuer(tokenConfig.issuer).build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(tokenConfig.audience)) JWTPrincipal(
                    credential.payload
                )
                else null
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }

    routing {
        //TODO change error messages
        post("/login") {
            val request = call.receive<AuthRequest>()

            val user = findUser(request.username) ?: run {
                call.respond(HttpStatusCode.Conflict, "Incorrect username")
                return@post
            }
            val isValidPassword = hashingService.verify(
                value = request.password,
                saltedHash = SaltedHash(hash = user.password, salt = user.salt)
            )
            if (!isValidPassword) {
                call.respond(HttpStatusCode.Conflict, "Incorrect password")
                return@post
            }

            val token = tokenService.create(
                tokenConfig = tokenConfig,
                tokenClaim = TokenClaim("username", request.username)
            )

            call.respond(HttpStatusCode.OK, AuthResponse(token))
        }

        post("/register") {
            val request = call.receive<AuthRequest>()

            val areFieldsBlank = request.username.isBlank() || request.password.isBlank()
            val isPwTooShort = request.password.length < 8
            if (areFieldsBlank || isPwTooShort) {
                call.respond(HttpStatusCode.Conflict, "Fields may not be blank")
                return@post
            }

            val userExists = findUser(request.username) != null
            if (userExists) {
                call.respond(HttpStatusCode.Conflict, "User already exists")
                return@post
            }

            val saltedHash = hashingService.generateSaltedHash(request.password)

            val user = User(
                username = request.username,
                password = saltedHash.hash,
                salt = saltedHash.salt
            )

            val wasAcknowledged = createUser(user)
            if (!wasAcknowledged) {
                call.respond(HttpStatusCode.Conflict)
                return@post
            }

            call.respond(HttpStatusCode.OK)
        }
    }
}