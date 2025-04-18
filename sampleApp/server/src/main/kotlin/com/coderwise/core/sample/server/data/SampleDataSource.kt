package com.coderwise.core.sample.server.data

import com.coderwise.core.sample.server.plugins.SampleDto

class SampleDataSource {

    suspend fun getSamples(): List<SampleDto> {
        return listOf(
            SampleDto(1, "value 1"),
            SampleDto(2, "value 2"),
            SampleDto(3, "value 3")
        )
    }
}