package com.coderwise.core.auth.data.remote

import com.coderwise.core.auth.domain.SessionRepository
import com.coderwise.core.data.remote.HttpException
import com.coderwise.core.domain.repository.NetworkError
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

suspend inline fun <reified T> authApiCall(
    session: SessionRepository,
    block: suspend () -> HttpResponse
): T = try {
    val httpResponse = block()
    if (httpResponse.status.isSuccess()) {
        httpResponse.body()
    } else {
        if (httpResponse.status.value == 401) {
            session.clear()
        }

        throw HttpException(httpResponse.status.value, httpResponse.status.description)
    }
} catch (e: HttpException) {
    throw e
} catch (e: Exception) {
    throw NetworkError(e.message ?: e.toString())
}
