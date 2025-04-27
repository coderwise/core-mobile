package com.coderwise.core.auth.data.remote

import com.coderwise.core.auth.domain.SessionRepository
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

suspend inline fun <reified T> authApiCall(session: SessionRepository, block: suspend () -> HttpResponse): T {
    return try {
        val httpResponse = block()
        if (httpResponse.status.isSuccess()) {
            httpResponse.body()
        } else {
            if (httpResponse.status.value == 401) {
                session.clear()
            }
            throw Exception(httpResponse.status.description)
        }
    } catch (e: Exception) {
        throw e
    }
}