package com.coderwise.core.data.arch

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

interface RemoteSource<Entity> {
    suspend fun fetch(): List<Entity>
}

class RepositoryImpl<Entity, Id>(
    private val local: LocalSource<Entity, Id>,
    private val remote: RemoteSource<Entity>? = null,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    fun flow(): Flow<List<Entity>> = local.flow.also {
        refreshFromRemote()
    }

    fun flowById(id: Id): Flow<Entity> = local.flowById(id)

    suspend fun update(trip: Entity): Id = local.update(trip)

    suspend fun delete(id: Id) {
        local.delete(id)
    }

    private fun refreshFromRemote() {
        scope.launch {
            if (local.isEmpty()) {
                remote?.fetch()?.let {
                    local.merge(it)
                }
            }
        }
    }
}