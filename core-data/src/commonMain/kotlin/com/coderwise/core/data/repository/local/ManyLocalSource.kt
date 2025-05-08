package com.coderwise.core.data.repository.local

import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.domain.repository.Identifiable
import kotlinx.coroutines.flow.Flow

interface ManyLocalSource<Id, Entity: Identifiable<Id>> {
    val flow: Flow<Outcome<List<Entity>>>
    fun flowById(id: Id): Flow<Outcome<Entity>>

    suspend fun create(entity: Entity): Outcome<Id>

    /**
     * Create a list of entities and add to local storage.
     */
    suspend fun createAll(list: List<Entity>): Outcome<Unit>

    suspend fun read(id: Id): Outcome<Entity>
    suspend fun readAll(): Outcome<List<Entity>>

    suspend fun update(entity: Entity): Outcome<Id>

    suspend fun delete(id: Id): Outcome<Unit>
    suspend fun deleteAll(): Outcome<Unit>
}