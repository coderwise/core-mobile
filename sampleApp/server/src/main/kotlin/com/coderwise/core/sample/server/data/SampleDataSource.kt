package com.coderwise.core.sample.server.data

import com.coderwise.core.sample.server.plugins.SampleDto

class SampleDataSource {

    private val samples = mutableListOf<SampleDto>()

    suspend fun getSamples(): List<SampleDto> {
        return samples
    }

    suspend fun getSample(id: Int): SampleDto? {
        return samples.find { it.id == id }
    }
}