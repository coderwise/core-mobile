package com.coderwise.core.sample.data.sample.local

import androidx.datastore.core.DataStore
import com.coderwise.core.sample.data.sample.Sample
import com.coderwise.core.sample.data.sample.SampleRecord
import com.coderwise.core.data.arch.DataStoreLocalSource
import com.coderwise.core.data.arch.DataStoreRecord
import com.coderwise.core.sample.data.sample.asDomainModel
import com.coderwise.core.sample.data.sample.asRecord

class DataStoreSampleSource(
    dataStore: DataStore<DataStoreRecord<SampleRecord>>
) : DataStoreLocalSource<Sample, Int, SampleRecord>(
    identify = { it.id },
    recordToEntity = { it.asDomainModel() },
    entityToRecord = { it.asRecord() },
    dataStore = dataStore
)