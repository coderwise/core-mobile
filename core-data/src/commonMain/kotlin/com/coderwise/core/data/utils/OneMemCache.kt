package com.coderwise.core.data.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class OneMemCache<Entity>(
    initial: Entity
) {
    private val cacheFlow = MutableStateFlow(initial)
    val flow: Flow<Entity> = cacheFlow

    fun read(): Entity = cacheFlow.value

    fun update(entity: Entity) {
        cacheFlow.tryEmit(entity)
    }

    fun update(block: (Entity) -> Entity) {
        cacheFlow.tryEmit(block(cacheFlow.value))
    }
}