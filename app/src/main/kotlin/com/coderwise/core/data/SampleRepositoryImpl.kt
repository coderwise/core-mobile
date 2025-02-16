package com.coderwise.core.data

import android.content.Context
import androidx.datastore.dataStoreFile
import com.coderwise.core.data.arch.MemoryLocalSource
import com.coderwise.core.data.arch.RepositoryImpl
import com.coderwise.core.domain.arch.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import okio.FileSystem
import okio.Path.Companion.toOkioPath

@Serializable
data class SampleRecord(val value: String)

class SampleRepositoryImpl(
    context: Context
) : SampleRepository {
    private val impl = RepositoryImpl<Sample, Int>(
        local = MemoryLocalSource(
            identify = { it.value.hashCode() }
        ),
        remote = null
    )

    private val dataStore = LocalStoreCreator.create(
        defaultValue = SampleRecord("test"),
        serializer = SampleRecord.serializer(),
        fileSystem = FileSystem.SYSTEM,
        producePath = {
            val file = context.dataStoreFile("${SampleRecord::class.java.simpleName}.pb")
            file.absoluteFile.toOkioPath()
        }
    )
    override val flow: Flow<Outcome<Sample>> =
        dataStore.data.map { Outcome.Success(Sample(value = it.value)) }
}
