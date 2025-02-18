package com.coderwise.core.data.arch

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.serialization.Serializable

@Serializable
data class DataStoreRecord<out Record : Any>(
    val list: List<Record>
)

open class DataStoreLocalSource<Entity, Id, Record : Any>(
    private val identify: (Entity) -> Id,
    private val mapToEntity: (Record) -> Entity,
    private val mapToRecord: (Entity) -> Record,
    private val dataStore: DataStore<DataStoreRecord<Record>>
) : LocalSource<Entity, Id> {

    override val flow: Flow<List<Entity>> =
        dataStore.data.map { data -> data.list.map { mapToEntity(it) } }

    override fun flowById(id: Id): Flow<Entity> = flow.transform { list ->
        try {
            emit(list.first { identify(it) == id })
        } catch (_: NoSuchElementException) {
            // NOOP
        }
    }

    override suspend fun update(entity: Entity): Id {
        val entityId = identify(entity)
        dataStore.updateData { data ->
            val record = mapToRecord(entity)
            if (data.findById(entityId) != null) {
                data.delete(entityId).copy(list = data.list + record)
            } else {
                data.copy(list = data.list + record)
            }
        }
        return entityId
    }

    override suspend fun delete(id: Id) {
        dataStore.updateData { data ->
            data.delete(id)
        }
    }

    override suspend fun merge(list: List<Entity>) {
        dataStore.updateData { data ->
            data.copy(list = list.map { mapToRecord(it) })
        }
    }

    override suspend fun isEmpty(): Boolean = dataStore.data.first().list.isEmpty()

    private fun DataStoreRecord<Record>.findById(id: Id): Entity? = list.map {
        mapToEntity(it)
    }.first {
        identify(it) == id
    }

    private fun DataStoreRecord<Record>.delete(id: Id) = copy(
        list = list.filter { identify(mapToEntity(it)) != id }
    )
}