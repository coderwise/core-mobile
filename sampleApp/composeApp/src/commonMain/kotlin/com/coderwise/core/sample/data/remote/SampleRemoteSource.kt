package com.coderwise.core.sample.data.remote

import com.coderwise.core.data.remote.UrlProvider
import com.coderwise.core.data.remote.apiCall
import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.domain.arch.tryOutcome
import com.coderwise.core.auth.data.remote.AuthTokenProvider
import com.coderwise.core.sample.data.Sample
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

class SampleRemoteSource(
    private val httpClient: HttpClient,
    private val baseUrl: UrlProvider,
    private val authToken: AuthTokenProvider
) {
    suspend fun fetchAll(): Outcome<List<Sample>> = tryOutcome {
        val list: List<SampleResponse> = apiCall {
            httpClient.get("${baseUrl.get()}/samples") {
                contentType(ContentType.Application.Json)
                bearerAuth(authToken.get())
            }
        }
        list.map { it.asDomainModel() }
    }
}

private fun SampleResponse.asDomainModel() = Sample(id, value)
