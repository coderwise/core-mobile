package com.coderwise.core.data.arch

import com.coderwise.core.domain.arch.Outcome

interface CrudLocalSource<Entity, Id> {
    suspend fun create(entity: Entity): Outcome<Id>

    suspend fun read(id: Id): Outcome<Entity>

    suspend fun update(entity: Entity): Outcome<Id>

    suspend fun delete(id: Id): Outcome<Unit>
}