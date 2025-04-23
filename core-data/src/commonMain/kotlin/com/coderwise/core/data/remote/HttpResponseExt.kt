package com.coderwise.core.data.remote

import com.coderwise.core.domain.arch.Session
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

data class HttpException(val code: Int, override val message: String) : Exception()

suspend inline fun <reified T> apiCall(block: suspend () -> HttpResponse): T {
    return try {
        val httpResponse = block()
        if (httpResponse.status.isSuccess()) {
            httpResponse.body()
        } else {
            throw HttpException(httpResponse.status.value, httpResponse.status.description)
        }
    } catch (e: Throwable) {
        throw e
    }
}


suspend inline fun <reified T> authApiCall(session: Session, block: suspend () -> HttpResponse): T {
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