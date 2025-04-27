package com.coderwise.core.sample.server

import com.coderwise.core.sample.server.plugins.configureAuthentication
import com.coderwise.core.sample.server.plugins.configureKoin
import com.coderwise.core.sample.server.plugins.configureLogging
import com.coderwise.core.sample.server.plugins.configureRoutes
import com.coderwise.core.sample.server.plugins.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureLogging()
    configureKoin()
    configureSerialization()
    configureAuthentication()
    configureRoutes()
}
