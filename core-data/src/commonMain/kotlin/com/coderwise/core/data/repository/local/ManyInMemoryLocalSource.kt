package com.coderwise.core.data.repository.local

import com.coderwise.core.data.repository.local.ManyLocalSource
import com.coderwise.core.data.utils.ManyMemCache
import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.domain.arch.tryOutcome
import com.coderwise.core.domain.repository.Identifiable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ManyInMemoryLocalSource<Id, Entity : Identifiable<Id>> : ManyLocalSource<Id, Entity> {
    private val memCache = ManyMemCache<Id, Entity>()

    override val flow: Flow<Outcome<List<Entity>>> = memCache.flow().map { Outcome.Success(it) }

    override fun flowById(id: Id): Flow<Outcome<Entity>> =
        memCache.flowById(id).map { tryOutcome { it!! } }

    override suspend fun create(entity: Entity): Outcome<Id> =
        tryOutcome { memCache.update(entity) }

    override suspend fun createAll(list: List<Entity>): Outcome<Unit> =
        tryOutcome { memCache.merge(list) }

    override suspend fun read(id: Id): Outcome<Entity> = tryOutcome { memCache.find(id)!! }

    override suspend fun readAll(): Outcome<List<Entity>> = tryOutcome { memCache.getAll()!! }

    override suspend fun update(entity: Entity): Outcome<Id> =
        tryOutcome { memCache.update(entity) }

    override suspend fun delete(id: Id): Outcome<Unit> = tryOutcome { memCache.delete(id) }

    override suspend fun deleteAll(): Outcome<Unit> = tryOutcome { memCache.clear() }
}