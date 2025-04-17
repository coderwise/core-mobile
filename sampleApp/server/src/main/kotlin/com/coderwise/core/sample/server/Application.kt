package com.coderwise.core.sample.server

import com.coderwise.core.auth.server.AuthConfig
import com.coderwise.core.auth.server.configureAuth
import com.coderwise.core.sample.server.plugins.configureLogging
import com.coderwise.core.sample.server.plugins.configureSerialization
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {

    configureLogging()
    configureSerialization()
    configureAuth(
        AuthConfig(
            jwtSecret = "secret",
            jwtAudience = "audience",
            jwtIssuer = "issuer",
            jwtExpiration = 3600000,
            realm = "realm"
        )
    )

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        authenticate {
            get("/sample") {
                val list = List(10) { SampleDto(it, "value $it") }
                call.respond(HttpStatusCode.OK, list)
            }
        }
    }
}

@Serializable
data class SampleDto(
    val id: Int,
    val value: String
)