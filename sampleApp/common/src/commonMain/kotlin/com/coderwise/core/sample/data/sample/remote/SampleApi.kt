package com.coderwise.core.sample.data.sample.remote

import com.coderwise.core.auth.data.remote.authApiCall
import com.coderwise.core.auth.domain.SessionRepository
import com.coderwise.core.sample.server.api.SampleDto
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class SampleApi(
    private val httpClient: HttpClient,
    private val sampleUrls: SampleUrls,
    private val session: SessionRepository
) {
    suspend fun create(sample: SampleDto): Int = authApiCall(session) {
        httpClient.post(sampleUrls.samples) {
            contentType(ContentType.Application.Json)
            bearerAuth(session.authToken())
            setBody(sample)
        }
    }

    suspend fun readAll(): List<SampleDto> = authApiCall(session) {
        httpClient.get(sampleUrls.samples) {
            contentType(ContentType.Application.Json)
            bearerAuth(session.authToken())
        }
    }

    suspend fun update(sample: SampleDto): Unit = authApiCall(session) {
        httpClient.patch("${sampleUrls.samples}/${sample.id}") {
            contentType(ContentType.Application.Json)
            bearerAuth(session.authToken())
            setBody(sample)
        }
    }

    suspend fun delete(id: Int): Unit = authApiCall(session) {
        httpClient.delete("${sampleUrls.samples}/$id") {
            contentType(ContentType.Application.Json)
            bearerAuth(session.authToken())
        }
    }
}