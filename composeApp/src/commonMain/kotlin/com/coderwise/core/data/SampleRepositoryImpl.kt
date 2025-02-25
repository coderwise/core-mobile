package com.coderwise.core.data

import com.coderwise.core.data.arch.LocalSource
import com.coderwise.core.data.arch.MemoryLocalSource
import com.coderwise.core.data.arch.RepositoryImpl
import com.coderwise.core.data.local.DataStoreSampleSource
import com.coderwise.core.domain.arch.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@Serializable
data class SampleRecord(
    val id: String,
    val value: String
)

class SampleRepositoryImpl(
    private val disk: DataStoreSampleSource
) : SampleRepository {
    private val impl = RepositoryImpl<Sample, Int>(
        local = MemoryLocalSource(
            identify = { it.value.hashCode() }
        ),
        remote = null
    )

    override val flow: Flow<Outcome<List<Sample>>> = disk.flow.map { list ->
        Outcome.Success(list)
    }

    override fun flowById(sampleId: String) = disk
        .flowById(sampleId)
        .map { Outcome.Success(it) }

    override suspend fun update(sample: Sample): Outcome<String> = Outcome.Success(
        disk.update(sample)
    )
}

fun Sample.asRecord() = SampleRecord(
    id = id,
    value = value
)

fun SampleRecord.asDomainModel() = Sample(
    id = id,
    value = value
)