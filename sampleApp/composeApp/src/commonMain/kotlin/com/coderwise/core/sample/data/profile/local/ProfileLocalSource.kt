package com.coderwise.core.sample.data.profile.local

import androidx.datastore.core.DataStore
import com.coderwise.core.data.repository.local.OneDataStoreSource
import com.coderwise.core.sample.data.profile.Profile
import kotlinx.serialization.Serializable

@Serializable
data class ProfileRecord(
    val userName: String = ""
)

class ProfileLocalSource(
    dataStore: DataStore<ProfileRecord>
) : OneDataStoreSource<Profile, ProfileRecord>(
    dataStore = dataStore,
    asEntity = { asEntity() },
    asRecord = { asRecord() }
)
