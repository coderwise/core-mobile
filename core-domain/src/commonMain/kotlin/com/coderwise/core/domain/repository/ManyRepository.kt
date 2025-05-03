package com.coderwise.core.domain.repository

import com.coderwise.core.domain.arch.Outcome
import kotlinx.coroutines.flow.Flow

interface Identifiable<Id> {
    val id: Id
}

enum class SyncStatus {
    Syncing,
    Synced,
    Error
}

interface ManyRepository<Id, Entity : Identifiable<Id>> {
    val syncStatus: Flow<SyncStatus>

    val flow: Flow<Outcome<List<Entity>>>

    fun flowById(id: Id): Flow<Outcome<Entity>>

    suspend fun create(entity: Entity): Outcome<Id>

    suspend fun read(id: Id): Outcome<Entity>

    suspend fun readAll(): Outcome<List<Entity>>

    suspend fun update(entity: Entity): Outcome<Unit>

    suspend fun delete(id: Id): Outcome<Unit>
}