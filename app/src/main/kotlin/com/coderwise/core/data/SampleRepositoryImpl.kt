package com.coderwise.core.data

import android.content.Context
import com.coderwise.core.data.arch.DataStoreLocalSource
import com.coderwise.core.data.arch.DataStoreRecord
import com.coderwise.core.data.arch.MemoryLocalSource
import com.coderwise.core.data.arch.RepositoryImpl
import com.coderwise.core.data.utils.createDataStore
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
    context: Context
) : SampleRepository {
    private val impl = RepositoryImpl<Sample, Int>(
        local = MemoryLocalSource(
            identify = { it.value.hashCode() }
        ),
        remote = null
    )

    private val dataStore = createDataStore(
        context = context,
        defaultValue = DataStoreRecord<SampleRecord>(
            List(10) { SampleRecord(it.toString(), "sample $it") }
        ),
        serializer = DataStoreRecord.serializer(SampleRecord.serializer())
    )

    private val dataStoreLocalSource = DataStoreLocalSource<Sample, String, SampleRecord>(
        identify = { it.id },
        recordToEntity = { it.asDomainModel() },
        entityToRecord = { it.asRecord() },
        dataStore = dataStore
    )

    override val flow: Flow<Outcome<List<Sample>>> = dataStoreLocalSource.flow.map { list ->
        Outcome.Success(list)
    }

    override fun flowById(sampleId: String) = dataStoreLocalSource
        .flowById(sampleId)
        .map { Outcome.Success(it) }

    override suspend fun update(sample: Sample): Outcome<String> = Outcome.Success(
        dataStoreLocalSource.update(sample)
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