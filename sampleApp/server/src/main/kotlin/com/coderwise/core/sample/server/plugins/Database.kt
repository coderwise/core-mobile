package com.coderwise.core.sample.server.plugins

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

data class SampleEntity(
    val id: Int,
    val value: String
)

fun Application.configureDatabase() {
    val clientSettings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString("mongodb://localhost:27017"))
        .build()

    val client = MongoClients.create(clientSettings)
    val database = client.getDatabase("myDatabase")

    val collection = database.getCollection("myCollection")

    routing {

    }
}