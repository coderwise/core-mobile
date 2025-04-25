package com.coderwise.core.sample.server.plugins

import com.coderwise.core.sample.server.data.SampleDataSource
import com.coderwise.core.sample.server.data.UserDataSource
import com.coderwise.core.sample.server.routes.sampleRoutes
import com.coderwise.core.sample.server.routes.userRoutes
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import kotlin.getValue

fun Application.configureRoutes() {
    val userDataSource by inject<UserDataSource>()
    val sampleDataSource by inject<SampleDataSource>()

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }

        authenticate {
            sampleRoutes(sampleDataSource)
            userRoutes(userDataSource)
        }
    }
}
