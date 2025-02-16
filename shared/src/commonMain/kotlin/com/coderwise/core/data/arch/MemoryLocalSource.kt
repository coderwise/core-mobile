package com.coderwise.core.data.arch

import com.coderwise.core.data.utils.MemCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class MemoryLocalSource<Entity, Id>(
    identify: (Entity) -> Id
) : LocalSource<Entity, Id> {
    private val memCache = MemCache(
        identify = identify
    )

    override val flow: Flow<List<Entity>> = memCache.flow()

    override fun flowById(id: Id): Flow<Entity> = memCache.flowById(id).transform {
        if (it == null) throw Exception("Entity not found")
        emit(it)
    }

    override suspend fun delete(id: Id) {
        memCache.delete(id)
    }

    override suspend fun merge(list: List<Entity>) {
        memCache.merge(list)
    }

    override suspend fun isEmpty(): Boolean = memCache.size() == 0

    override suspend fun update(entity: Entity): Id = memCache.update(entity)
}