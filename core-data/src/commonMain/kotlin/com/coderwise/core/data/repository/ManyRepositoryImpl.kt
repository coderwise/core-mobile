package com.coderwise.core.data.repository

import com.coderwise.core.data.repository.local.ManyInMemoryLocalSource
import com.coderwise.core.data.repository.local.ManyLocalSource
import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.domain.arch.onError
import com.coderwise.core.domain.arch.onSuccess
import com.coderwise.core.domain.arch.then
import com.coderwise.core.domain.repository.Identifiable
import com.coderwise.core.domain.repository.ManyRepository
import com.coderwise.core.domain.repository.SyncStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart

interface SyncWithRemoteWorker<Id> {
    val syncStatus: Flow<SyncStatus>

    fun sync(id: Id)
}

open class ManyRepositoryImpl<Id, Entity : Identifiable<Id>>(
    private val localSource: ManyLocalSource<Id, Entity> = ManyInMemoryLocalSource(),
    private val remoteSource: ManyRemoteSource<Id, Entity>? = null
) : ManyRepository<Id, Entity> {
    private val syncWorker = object : SyncWithRemoteWorker<Id> {
        override val syncStatus: Flow<SyncStatus>
            get() = flowOf(SyncStatus.Synced)

        override fun sync(id: Id) {

        }
    }
    private val syncStatusCache = MutableStateFlow(SyncStatus.Synced)

    override val syncStatus: Flow<SyncStatus> = syncStatusCache

    override val flow: Flow<Outcome<List<Entity>>> = localSource.flow.onStart {
        remoteSource?.readAll()?.onSuccess { localSource.createAll(it) }
    }

    override fun flowById(id: Id): Flow<Outcome<Entity>> = localSource.flowById(id)

    override suspend fun create(entity: Entity): Outcome<Id> =
        localSource.create(entity).then { localId ->
            syncStatusCache.value = SyncStatus.Syncing
            remoteSource?.create(entity)?.onError {
                syncStatusCache.value = SyncStatus.Error
            } ?: Outcome.Success(localId)
        }

    override suspend fun read(id: Id): Outcome<Entity> = localSource.read(id)

    override suspend fun readAll(): Outcome<List<Entity>> = localSource.readAll()

    override suspend fun update(entity: Entity): Outcome<Id> =
        localSource.update(entity).then { localId ->
            syncStatusCache.value = SyncStatus.Syncing
            remoteSource?.update(entity)?.onError {
                syncStatusCache.value = SyncStatus.Error
            } ?: Outcome.Success(localId)
        }

    override suspend fun delete(id: Id): Outcome<Unit> = localSource.delete(id).then {
        syncStatusCache.value = SyncStatus.Syncing
        remoteSource?.delete(id)?.onError {
            syncStatusCache.value = SyncStatus.Error
        } ?: Outcome.Success(Unit)
    }
}