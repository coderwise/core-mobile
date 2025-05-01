package com.coderwise.core.data.repository.local

import androidx.datastore.core.DataStore
import com.coderwise.core.data.repository.OneSource
import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.domain.arch.tryOutcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

open class OneDataStoreSource<Entity, Record>(
    private val dataStore: DataStore<Record>,
    private val asEntity: Record.() -> Entity,
    private val asRecord: Entity.() -> Record
) : OneSource<Entity> {
    override val flow: Flow<Outcome<Entity>> = dataStore.data.map { Outcome.Success(it.asEntity()) }

    override suspend fun read(): Outcome<Entity> = flow.first()

    override suspend fun update(entity: Entity): Outcome<Unit> = tryOutcome {
        dataStore.updateData { entity.asRecord() }
    }
}