package com.coderwise.core.data.repository

import com.coderwise.core.domain.arch.Outcome
import kotlinx.coroutines.flow.Flow

interface OneSource<Entity> {
    val flow: Flow<Outcome<Entity>>
    suspend fun read(): Outcome<Entity>
    suspend fun update(entity: Entity): Outcome<Unit>
}