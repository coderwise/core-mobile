package com.coderwise.core.sample.data

import com.coderwise.core.domain.arch.Outcome
import kotlinx.coroutines.flow.Flow

data class Sample(
    val id: Int,
    val value: String
)

interface SampleRepository {
    fun flowById(sampleId: Int): Flow<Outcome<Sample>>

    val flow: Flow<Outcome<List<Sample>>>

    suspend fun update(sample: Sample): Outcome<Int>

    suspend fun delete(id: Int): Outcome<Unit>

    suspend fun fetchAll(): Outcome<Unit>
}