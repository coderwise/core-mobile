package com.coderwise.core.data.local

import androidx.datastore.core.DataStore
import com.coderwise.core.data.Sample
import com.coderwise.core.data.SampleRecord
import com.coderwise.core.data.arch.DataStoreLocalSource
import com.coderwise.core.data.arch.DataStoreRecord
import com.coderwise.core.data.asDomainModel
import com.coderwise.core.data.asRecord

class DataStoreSampleSource(
    dataStore: DataStore<DataStoreRecord<SampleRecord>>
) : DataStoreLocalSource<Sample, String, SampleRecord>(
    identify = { it.id },
    recordToEntity = { it.asDomainModel() },
    entityToRecord = { it.asRecord() },
    dataStore = dataStore
)