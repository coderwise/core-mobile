package com.coderwise.core.data.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlin.collections.ifEmpty

class MemCache<Entity, Id>(
    private val identify: (Entity) -> Id
) {
    private val cacheFlow = MutableStateFlow(mapOf<Id, Entity>())

    fun flow(): Flow<List<Entity>> = cacheFlow.map { it.values.toList() }

    fun flowById(id: Id): Flow<Entity?> = cacheFlow.map { it[id] }

    fun set(list: List<Entity>) {
        cacheFlow.value = list.associateBy { identify(it) }
    }

    fun getAll(): List<Entity>? = cacheFlow.value.values.toList().ifEmpty { null }

    fun delete(id: Id) {
        val mutableCopy = cacheFlow.value.toMutableMap()
        mutableCopy.remove(id)
        cacheFlow.value = mutableCopy
    }

    fun update(entity: Entity): Id {
        val id = identify(entity)
        cacheFlow.update {
            it.toMutableMap().apply {
                this[id] = entity
            }
        }
        return id
    }

    fun merge(list: List<Entity>) {
        val mutableCopy = cacheFlow.value.toMutableMap()
        mutableCopy.putAll(list.associateBy { identify(it) })
        cacheFlow.value = mutableCopy
    }

    fun find(id: Id): Entity? = cacheFlow.value[id]

    fun isEmpty() = cacheFlow.value.isEmpty()

    fun clear() {
        cacheFlow.value = mapOf()
    }

    fun size() = cacheFlow.value.entries.size
}
