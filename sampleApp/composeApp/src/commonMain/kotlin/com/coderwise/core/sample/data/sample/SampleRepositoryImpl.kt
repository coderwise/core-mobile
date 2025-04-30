package com.coderwise.core.sample.data.sample

import com.coderwise.core.data.arch.MemoryLocalSource
import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.domain.arch.mapSuccess
import com.coderwise.core.domain.arch.onSuccess
import com.coderwise.core.sample.data.sample.remote.SampleRemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Serializable
data class SampleRecord(
    val id: Int,
    val value: String
)

class SampleRepositoryImpl(
    private val network: SampleRemoteSource
) : SampleRepository {
    private val memory = MemoryLocalSource<Sample, Int>(
        identify = { it.id }
    )
    override val flow: Flow<Outcome<List<Sample>>> = memory.flow //.onStart { fetchAll() }

    override fun flowById(sampleId: Int) = memory.flowById(sampleId)

    override suspend fun update(sample: Sample): Outcome<Int> = network.update(sample).onSuccess {
        fetchAll()
    }

    override suspend fun delete(id: Int): Outcome<Unit> = network.delete(id).onSuccess {
        fetchAll()
    }

    override suspend fun fetchAll(): Outcome<Unit> = network.fetchAll().onSuccess {
        memory.reset(it)
    }.mapSuccess { }
}

fun Sample.asRecord() = SampleRecord(
    id = id,
    value = value
)

fun SampleRecord.asDomainModel() = Sample(
    id = id,
    value = value
)