package com.coderwise.core.sample.server

import com.coderwise.core.auth.server.JwtConfig
import com.coderwise.core.auth.server.User
import com.coderwise.core.auth.server.configureAuth
import com.coderwise.core.sample.server.plugins.configureAuthentication
import com.coderwise.core.sample.server.plugins.configureDatabase
import com.coderwise.core.sample.server.plugins.configureLogging
import com.coderwise.core.sample.server.plugins.configureRoutes
import com.coderwise.core.sample.server.plugins.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import kotlin.time.Duration.Companion.minutes

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {

    configureLogging()
    configureSerialization()
    configureAuthentication()
    configureRoutes()
    configureDatabase()
}
