package com.coderwise.core.data.arch

import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.domain.arch.onError
import com.coderwise.core.domain.arch.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

interface RemoteSource<Entity> {
    suspend fun fetchList(): Outcome<List<Entity>>
}

class CollectionRepositoryImpl<Entity, Id>(
    private val local: LocalSource<Entity, Id>,
    private val remote: RemoteSource<Entity>? = null,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    fun flow(): Flow<Outcome<List<Entity>>> = local.flow.also {
        refreshFromRemote()
    }

    fun flowById(id: Id): Flow<Outcome<Entity>> = local.flowById(id)

    suspend fun update(trip: Entity): Outcome<Id> = local.update(trip)

    suspend fun delete(id: Id): Outcome<Unit> = local.delete(id)

    private fun refreshFromRemote() {
        scope.launch {
            if (local.isEmpty()) {
                remote?.fetchList()?.let { outcome ->
                    outcome.onSuccess {
                        local.merge(it)
                    }.onError {

                    }
                }
            }
        }
    }
}