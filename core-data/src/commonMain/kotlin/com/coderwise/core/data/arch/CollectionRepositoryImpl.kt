package com.coderwise.core.data.arch

import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.domain.arch.onError
import com.coderwise.core.domain.arch.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class CollectionRepositoryImpl<Entity, Id, FetchQuery>(
    private val local: LocalSource<Entity, Id>,
    private val remote: RemoteSource<Entity, FetchQuery>? = null,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    private var fetchQuery: FetchQuery? = null

    fun flow(): Flow<Outcome<List<Entity>>> = local.flow.also {
        refreshFromRemote()
    }

    fun flowById(id: Id): Flow<Outcome<Entity>> = local.flowById(id)

    suspend fun update(trip: Entity): Outcome<Id> = local.update(trip)

    suspend fun delete(id: Id): Outcome<Unit> = local.delete(id)

    private fun refreshFromRemote() {
        scope.launch {
            if (local.isEmpty()) {
                remote?.fetchList(fetchQuery)?.let { outcome ->
                    outcome.onSuccess {
                        local.merge(it)
                    }.onError {

                    }
                }
            }
        }
    }
}