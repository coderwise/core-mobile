package com.coderwise.core.data.arch

import com.coderwise.core.domain.arch.Outcome
import kotlinx.coroutines.flow.Flow

interface LocalSource<Entity, Id> {
    val flow: Flow<Outcome<List<Entity>>>
    fun flowById(id: Id): Flow<Outcome<Entity>>
    suspend fun update(entity: Entity): Outcome<Id>
    suspend fun delete(id: Id): Outcome<Unit>
    suspend fun merge(list: List<Entity>): Outcome<Unit>
    suspend fun reset(list: List<Entity>): Outcome<Unit>
    suspend fun isEmpty(): Boolean
    suspend fun clear(): Outcome<Unit> = merge(emptyList())
}
