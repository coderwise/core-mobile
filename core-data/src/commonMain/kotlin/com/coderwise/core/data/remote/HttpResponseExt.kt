package com.coderwise.core.data.remote

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

suspend inline fun <reified T> apiCall(block: suspend () -> HttpResponse): T {
    return try {
        val httpResponse = block()
        if (httpResponse.status.isSuccess()) {
            httpResponse.body()
        } else {
            throw Exception(httpResponse.status.description)
        }
    } catch (e: Exception) {
        throw Exception(e.message)
    }
}