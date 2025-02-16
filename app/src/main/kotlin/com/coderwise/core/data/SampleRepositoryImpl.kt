package com.coderwise.core.data

import android.content.Context
import androidx.datastore.dataStoreFile
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
    private val dataStore = LocalStoreCreator.create(
        defaultValue = SampleRecord("test"),
        serializer = SampleRecord.serializer(),
        //deserializer = Sample.serializer(),
        fileSystem = FileSystem.SYSTEM,
        producePath = {
            val file = context.dataStoreFile("${SampleRecord::class.java.simpleName}.pb")
            file.absoluteFile.toOkioPath()
        }
    )
    override val flow: Flow<Sample> = dataStore.data.map { Sample(value = it.value) }
}
