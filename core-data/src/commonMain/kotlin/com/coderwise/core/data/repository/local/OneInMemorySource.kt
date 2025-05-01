package com.coderwise.core.data.repository.local

import com.coderwise.core.data.repository.OneSource
import com.coderwise.core.data.utils.OneMemCache
import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.domain.arch.tryOutcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OneInMemorySource<Entity> : OneSource<Entity> {
    private val memCache = OneMemCache<Entity?>(null)

    override val flow: Flow<Outcome<Entity>> = memCache.flow.map { Outcome.Success(it!!) }

    override suspend fun read(): Outcome<Entity> = tryOutcome { memCache.read()!! }

    override suspend fun update(entity: Entity): Outcome<Unit> =
        tryOutcome { memCache.update(entity) }
}