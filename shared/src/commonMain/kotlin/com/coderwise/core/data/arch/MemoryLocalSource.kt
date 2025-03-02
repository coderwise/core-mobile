package com.coderwise.core.data.arch

import com.coderwise.core.data.utils.MemCache
import com.coderwise.core.domain.arch.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

open class MemoryLocalSource<Entity, Id>(
    identify: (Entity) -> Id
) : LocalSource<Entity, Id> {
    private val memCache = MemCache(
        identify = identify
    )

    override val flow: Flow<Outcome<List<Entity>>> = memCache.flow().map { Outcome.Success(it) }

    override fun flowById(id: Id): Flow<Outcome<Entity>> = memCache.flowById(id).transform {
        if (it == null) throw Exception("Entity not found")
        emit(Outcome.Success(it))
    }

    override suspend fun delete(id: Id): Outcome<Unit> {
        memCache.delete(id)
        return Outcome.Success(Unit)
    }

    override suspend fun merge(list: List<Entity>): Outcome<Unit> {
        memCache.merge(list)
        return Outcome.Success(Unit)
    }

    override suspend fun isEmpty(): Boolean = memCache.size() == 0

    override suspend fun update(entity: Entity): Outcome<Id> =
        Outcome.Success(memCache.update(entity))
}