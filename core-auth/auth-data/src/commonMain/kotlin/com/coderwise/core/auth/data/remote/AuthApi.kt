package com.coderwise.core.auth.data.remote

import com.coderwise.core.auth.api.AuthRequest
import com.coderwise.core.auth.api.AuthResponse
import com.coderwise.core.data.remote.UrlProvider
import com.coderwise.core.data.remote.apiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApi(
    private val httpClient: HttpClient,
    private val baseUrl: UrlProvider
) {
    suspend fun login(userName: String, password: String): AuthResponse = apiCall {
        httpClient.post("${baseUrl.get()}/login") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(userName, password))
        }
    }

    suspend fun register(userName: String, password: String): Unit = apiCall {
        httpClient.post("${baseUrl.get()}/register") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(userName, password))
        }
    }
}