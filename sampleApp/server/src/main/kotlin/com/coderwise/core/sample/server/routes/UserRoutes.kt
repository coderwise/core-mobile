package com.coderwise.core.sample.server.routes

import com.coderwise.core.auth.server.User
import com.coderwise.core.sample.server.data.UserDataSource
import com.coderwise.core.sample.server.data.model.UserResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.util.reflect.TypeInfo

private fun User.asResponse() = UserResponse(
    id = id!!,
    username = username
)

fun Route.userRoutes() {
    val userDataSource = UserDataSource()

    route("/users") {
        get("{id?}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respondText("Missing id")
                return@get
            }
            val user = userDataSource.getUserById(id.toInt())
            if (user == null) {
                call.respondText("User not found")
                return@get
            }
            call.respond(HttpStatusCode.OK, user.asResponse())
        }
    }
}