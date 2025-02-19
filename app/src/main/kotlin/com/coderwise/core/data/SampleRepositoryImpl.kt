package com.coderwise.core.data

import android.content.Context
import androidx.datastore.dataStoreFile
import com.coderwise.core.data.arch.DataStoreLocalSource
import com.coderwise.core.data.arch.DataStoreRecord
import com.coderwise.core.data.arch.MemoryLocalSource
import com.coderwise.core.data.arch.RepositoryImpl
import com.coderwise.core.data.utils.DataStoreCreator
import com.coderwise.core.domain.arch.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import okio.FileSystem
import okio.Path.Companion.toOkioPath

@Serializable
data class SampleRecord(
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

    private val dataStore = DataStoreCreator.create(
        defaultValue = DataStoreRecord<SampleRecord>(
            List(10) { SampleRecord("sample $it") }
        ),
        serializer = DataStoreRecord.serializer(SampleRecord.serializer()),
        fileSystem = FileSystem.SYSTEM,
        producePath = {
            val file = context.dataStoreFile("${SampleRecord::class.java.simpleName}.pb")
            file.absoluteFile.toOkioPath()
        }
    )

    private val dataStoreLocalSource = DataStoreLocalSource<Sample, Int, SampleRecord>(
        identify = { it.value.hashCode() },
        mapToEntity = { it.asDomainModel() },
        mapToRecord = { it.asRecord() },
        dataStore = dataStore
    )

    override val flow: Flow<Outcome<List<Sample>>> =
        dataStoreLocalSource.flow.map { list -> Outcome.Success(list) }
}

private fun Sample.asRecord() = SampleRecord(
    value = value
)

private fun SampleRecord.asDomainModel() = Sample(
    value = value
)