package com.coderwise.core.data.arch

import com.coderwise.core.domain.arch.Outcome

interface RemoteSource<Entity, Query> {
    suspend fun fetchList(query: Query?): Outcome<List<Entity>>
}
