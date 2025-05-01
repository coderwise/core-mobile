package com.coderwise.core.data.repository

import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.domain.repository.Identifiable

interface ManyRemoteSource<Id, Entity : Identifiable<Id>> {
    suspend fun create(entity: Entity): Outcome<Id>
    suspend fun readAll(): Outcome<List<Entity>>
    suspend fun update(entity: Entity): Outcome<Id>
    suspend fun delete(id: Id): Outcome<Unit>
}