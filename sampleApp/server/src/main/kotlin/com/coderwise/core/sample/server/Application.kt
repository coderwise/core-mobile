package com.coderwise.core.sample.server

import com.coderwise.core.auth.server.JWTConfig
import com.coderwise.core.auth.server.configureAuth
import com.coderwise.core.sample.server.plugins.configureLogging
import com.coderwise.core.sample.server.plugins.configureRoutes
import com.coderwise.core.sample.server.plugins.configureSerialization
import io.ktor.server.application.Application
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.minutes

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {

    configureLogging()
    configureSerialization()
    configureAuth(
        JWTConfig(
            secret = "secret",
            audience = environment.config.property("jwt.audience").getString(),
            issuer = environment.config.property("jwt.issuer").getString(),
            expiration = 60.minutes,
            realm = environment.config.property("jwt.realm").getString()
        )
    )
    configureRoutes()
}
