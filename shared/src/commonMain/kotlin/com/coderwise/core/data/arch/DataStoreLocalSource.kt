package com.coderwise.core.data.arch

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.serialization.Serializable
import kotlin.collections.first
import kotlin.collections.map

@Serializable
data class DataStoreRecord<Record : Any>(
    val list: List<Record>
)

open class DataStoreLocalSource<Entity, Id, Record : Any>(
    private val identify: (Entity) -> Id,
    private val recordToEntity: (Record) -> Entity,
    private val entityToRecord: (Entity) -> Record,
    protected val dataStore: DataStore<DataStoreRecord<Record>>
) : LocalSource<Entity, Id> {

    override val flow: Flow<List<Entity>> =
        dataStore.data.map { data -> data.list.map { recordToEntity(it) } }

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
            data.upsert(entity)
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
            data.copy(list = list.map { entityToRecord(it) })
        }
    }

    override suspend fun isEmpty(): Boolean = dataStore.data.first().list.isEmpty()

    private fun DataStoreRecord<Record>.upsert(
        entity: Entity
    ) = copy(
        list = list.upsert(entity, identify, recordToEntity, entityToRecord)
    )

    private fun DataStoreRecord<Record>.delete(id: Id) = copy(
        list = list.filter { identify(recordToEntity(it)) != id }
    )
}

fun <Entity, Id, Record> List<Record>.upsert(
    entity: Entity,
    identify: (Entity) -> Id,
    recordToEntity: (Record) -> Entity,
    entityToRecord: (Entity) -> Record
): List<Record> {
    val entityId = identify(entity)
    return if (findById(entityId, identify, recordToEntity) != null) {
        delete(entityId, identify, recordToEntity) + entityToRecord(entity)
    } else {
        this + entityToRecord(entity)
    }
}

fun <Entity, Id, Record> List<Record>.findById(
    id: Id,
    identify: (Entity) -> Id,
    recordToEntity: (Record) -> Entity
): Entity? = map {
    recordToEntity(it)
}.find {
    id == identify(it)
}

fun <Entity, Id, Record> List<Record>.delete(
    id: Id,
    identify: (Entity) -> Id,
    recordToEntity: (Record) -> Entity
) = filter { identify(recordToEntity(it)) != id }