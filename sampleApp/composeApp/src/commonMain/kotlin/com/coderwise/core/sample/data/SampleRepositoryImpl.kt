package com.coderwise.core.sample.data

import com.coderwise.core.data.arch.CollectionRepositoryImpl
import com.coderwise.core.data.arch.MemoryLocalSource
import com.coderwise.core.sample.data.local.DataStoreSampleSource
import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.domain.arch.mapSuccess
import com.coderwise.core.domain.arch.onSuccess
import com.coderwise.core.auth.data.remote.AuthTokenProvider
import com.coderwise.core.sample.data.remote.SampleRemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Serializable
data class SampleRecord(
    val id: Int,
    val value: String
)

class SampleRepositoryImpl(
    disk: DataStoreSampleSource,
    private val network: SampleRemoteSource
) : SampleRepository {
    private val memory = MemoryLocalSource<Sample, Int>(
        identify = { it.id }
    )
    private val impl = CollectionRepositoryImpl<Sample, Int, Unit>(
        local = disk,
        remote = null
    )

    override val flow: Flow<Outcome<List<Sample>>> = memory.flow

    override fun flowById(sampleId: Int) = memory.flowById(sampleId)

    override suspend fun update(sample: Sample): Outcome<Int> = memory.update(sample)

    override suspend fun reset() {
        memory.reset(listOf())
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