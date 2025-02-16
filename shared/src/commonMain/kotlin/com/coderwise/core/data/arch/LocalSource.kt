package com.coderwise.core.data.arch

import kotlinx.coroutines.flow.Flow

interface LocalSource<Entity, Id> {
    val flow: Flow<List<Entity>>
    fun flowById(id: Id): Flow<Entity>
    suspend fun update(entity: Entity): Id
    suspend fun delete(id: Id)
    suspend fun merge(list: List<Entity>)
    suspend fun isEmpty(): Boolean
}
