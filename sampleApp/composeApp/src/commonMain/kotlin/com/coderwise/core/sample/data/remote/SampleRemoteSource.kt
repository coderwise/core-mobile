package com.coderwise.core.sample.data.remote

import com.coderwise.core.auth.data.remote.authApiCall
import com.coderwise.core.auth.domain.SessionRepository
import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.domain.arch.tryOutcome
import com.coderwise.core.sample.data.Sample
import com.coderwise.core.sample.server.api.SampleDto
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class SampleRemoteSource(
    private val httpClient: HttpClient,
    private val sampleUrls: SampleUrls,
    private val session: SessionRepository
) {
    suspend fun fetchAll(): Outcome<List<Sample>> = tryOutcome {
        val list: List<SampleDto> = authApiCall(session) {
            httpClient.get(sampleUrls.samples) {
                contentType(ContentType.Application.Json)
                bearerAuth(session.authToken())
            }
        }
        list.map { it.asDomainModel() }
    }

    suspend fun update(sample: Sample): Outcome<Int> = tryOutcome {
        authApiCall(session) {
            httpClient.post(sampleUrls.samples) {
                contentType(ContentType.Application.Json)
                bearerAuth(session.authToken())
                setBody(sample.asDto())
            }
        }
    }

    suspend fun delete(id: Int): Outcome<Unit> = tryOutcome {
        authApiCall(session) {
            httpClient.delete("${sampleUrls.samples}/$id") {
                contentType(ContentType.Application.Json)
                bearerAuth(session.authToken())
            }
        }
    }

    private fun Sample.asDto() = SampleDto(id, value)
}

private fun SampleDto.asDomainModel() = Sample(id, value)
