package com.coderwise.core.data

import com.coderwise.core.data.arch.CollectionRepositoryImpl
import com.coderwise.core.data.arch.MemoryLocalSource
import com.coderwise.core.data.local.DataStoreSampleSource
import com.coderwise.core.domain.arch.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Serializable
data class SampleRecord(
    val id: String,
    val value: String
)

class SampleRepositoryImpl(
    private val disk: DataStoreSampleSource
) : SampleRepository {
    private val memory = MemoryLocalSource<Sample, Int>(
        identify = { it.value.hashCode() }
    )
    private val impl = CollectionRepositoryImpl<Sample, String>(
        local = disk,
        remote = null
    )

    override val flow: Flow<Outcome<List<Sample>>> = disk.flow

    override fun flowById(sampleId: String) = disk.flowById(sampleId)

    override suspend fun update(sample: Sample): Outcome<String> = disk.update(sample)
}

fun Sample.asRecord() = SampleRecord(
    id = id,
    value = value
)

fun SampleRecord.asDomainModel() = Sample(
    id = id,
    value = value
)