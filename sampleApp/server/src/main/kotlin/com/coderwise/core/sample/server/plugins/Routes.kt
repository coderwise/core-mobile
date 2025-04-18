package com.coderwise.core.sample.server.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable

fun Application.configureRoutes() {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        authenticate {
            get("/samples") {
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
