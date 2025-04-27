package com.coderwise.core.sample.server.routes

import com.coderwise.core.sample.server.api.SampleDto
import com.coderwise.core.sample.server.data.SampleDataSource
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.sampleRoutes(
    sampleDataSource: SampleDataSource
) {
    route("/samples") {
        get {
            call.respond(HttpStatusCode.OK, sampleDataSource.read())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val sample = sampleDataSource.read(id)
            if (sample == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            call.respond(HttpStatusCode.OK, sample)
        }

        post {
            val sample = call.receive<SampleDto>()
            sampleDataSource.create(sample)
            call.respond(HttpStatusCode.OK, sample.id)
        }

        patch("/{id?}") {
            val id = call.parameters["id"] ?: return@patch call.respondText(
                text = "Missing id",
                status = HttpStatusCode.BadRequest
            )
        }

        delete("/{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                text = "Missing id",
                status = HttpStatusCode.BadRequest
            )
            sampleDataSource.delete(id.toInt())
            call.respond(HttpStatusCode.OK)
        }
    }
}