package com.coderwise.core.sample.data.profile.local

import androidx.datastore.core.DataStore
import com.coderwise.core.data.repository.OneSource
import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.domain.arch.tryOutcome
import com.coderwise.core.sample.data.profile.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@Serializable
data class ProfileRecord(
    val userName: String = ""
)

class ProfileLocalSource(
    private val dataStore: DataStore<ProfileRecord>
) : OneSource<Profile> {
    override val flow: Flow<Outcome<Profile>> =
        dataStore.data.map { Outcome.Success(it.asEntity()) }

    override suspend fun read(): Outcome<Profile> = flow.first()

    override suspend fun update(entity: Profile): Outcome<Unit> = tryOutcome {
        dataStore.updateData { entity.asRecord() }
    }
}
