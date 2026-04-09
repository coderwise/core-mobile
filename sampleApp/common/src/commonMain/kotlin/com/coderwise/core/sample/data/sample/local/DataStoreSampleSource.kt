package com.coderwise.core.sample.data.sample.local

import androidx.datastore.core.DataStore
import com.coderwise.core.data.arch.DataStoreLocalSource
import com.coderwise.core.data.arch.DataStoreRecord
import com.coderwise.core.sample.data.sample.Sample
import kotlinx.serialization.Serializable

@Serializable
data class SampleRecord(
    val id: Int,
    val value: String
)

class DataStoreSampleSource(
    dataStore: DataStore<DataStoreRecord<SampleRecord>>
) : DataStoreLocalSource<Sample, Int, SampleRecord>(
    identify = { it.id },
    recordToEntity = { it.asDomainModel() },
    entityToRecord = { it.asRecord() },
    dataStore = dataStore
)

fun Sample.asRecord() = SampleRecord(
    id = id,
    value = value
)

fun SampleRecord.asDomainModel() = Sample(
    id = id,
    value = value
)