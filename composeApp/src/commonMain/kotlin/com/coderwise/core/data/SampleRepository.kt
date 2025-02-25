package com.coderwise.core.data

import com.coderwise.core.domain.arch.Outcome
import kotlinx.coroutines.flow.Flow

data class Sample(
    val id: String,
    val value: String
)

interface SampleRepository {
    fun flowById(sampleId: String): Flow<Outcome<Sample>>

    val flow: Flow<Outcome<List<Sample>>>

    suspend fun update(sample: Sample): Outcome<String>
}