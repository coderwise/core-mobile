package com.coderwise.core.data.repository.remote

import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.domain.repository.Identifiable

interface ManyRemoteSource<Id, Entity : Identifiable<Id>> {

    /**
     * Create new entity in remote storage.
     */
    suspend fun create(entity: Entity): Outcome<Id> {
        TODO()
    }

    suspend fun readAll(): Outcome<List<Entity>>

    suspend fun update(entity: Entity): Outcome<Unit> {
        TODO()
    }

    suspend fun delete(id: Id): Outcome<Unit> {
        TODO()
    }
}