package com.coderwise.core.sample.server.data

import com.coderwise.core.sample.server.api.SampleResponse


class SampleDataSource {

    private val samples = mutableListOf<SampleResponse>()

    suspend fun getSamples(): List<SampleResponse> {
        return samples
    }

    suspend fun getSample(id: Int): SampleResponse? {
        return samples.find { it.id == id }
    }
}