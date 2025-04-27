package com.coderwise.core.data.remote

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
