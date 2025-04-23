package com.coderwise.core.sample.server.plugins

import com.coderwise.core.sample.server.routes.sampleRoutes
import com.coderwise.core.sample.server.data.SampleDataSource
import com.coderwise.core.sample.server.routes.userRoutes
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable

fun Application.configureRoutes() {
    val sampleDataSource = SampleDataSource()
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }

        authenticate {
            sampleRoutes()
            userRoutes()
        }
    }
}

@Serializable
data class SampleDto(
    val id: Int,
    val value: String
)
