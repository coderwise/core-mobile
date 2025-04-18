package com.coderwise.core.sample.server.plugins

import com.coderwise.core.auth.server.JwtConfig
import com.coderwise.core.auth.server.configureAuth
import com.coderwise.core.sample.server.data.UserDataSource
import io.ktor.server.application.Application
import kotlin.time.Duration.Companion.minutes

fun Application.configureAuthentication() {
    val jwtConfig = JwtConfig(
        secret = "secret",
        audience = environment.config.property("jwt.audience").getString(),
        issuer = environment.config.property("jwt.issuer").getString(),
        expiration = 60.minutes,
        realm = environment.config.property("jwt.realm").getString()
    )

    val userDataSource = UserDataSource()

    configureAuth(
        tokenConfig = jwtConfig,
        findUser = { username -> userDataSource.getUserByUsername(username) },
        createUser = { user -> userDataSource.insertUser(user) }
    )
}