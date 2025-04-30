package com.coderwise.core.data.repository

import com.coderwise.core.domain.arch.Outcome
import com.coderwise.core.domain.arch.onSuccess
import kotlinx.coroutines.flow.Flow

open class OneRepositoryImpl<Entity>(
    private val localSource: OneSource<Entity>,
    private val remoteSource: OneSource<Entity>? = null
) {
    val flow: Flow<Outcome<Entity>> = localSource.flow

    suspend fun read(): Outcome<Entity> = localSource.read().onSuccess {
        refreshFromRemote()
    }

    suspend fun update(entity: Entity): Outcome<Unit> = localSource.update(entity).onSuccess {
        remoteSource?.update(entity)
    }

    private suspend fun refreshFromRemote() {
        remoteSource?.read()?.onSuccess {
            localSource.update(it)
        }
    }
}