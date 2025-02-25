package com.coderwise.core.data.utils

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioSerializer
import androidx.datastore.core.okio.OkioStorage
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.protobuf.ProtoBuf
import okio.BufferedSink
import okio.BufferedSource
import okio.FileSystem
import okio.Path

interface DataStorePathProducer {
    fun producePath(fileName: String): Path
}

object DataStoreCreator {
    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified Entity> create(
        defaultValue: Entity,
        serializer: KSerializer<Entity>,
        fileSystem: FileSystem,
        noinline producePath: () -> Path
    ): DataStore<Entity> = DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = fileSystem,
            serializer = object : OkioSerializer<Entity> {
                override val defaultValue: Entity = defaultValue

                override suspend fun readFrom(source: BufferedSource): Entity {
                    return ProtoBuf.Default.decodeFromByteArray(serializer, source.readByteArray())
                }

                override suspend fun writeTo(t: Entity, sink: BufferedSink) {
                    sink.write(ProtoBuf.Default.encodeToByteArray(serializer, t))
                }
            },
            producePath = producePath
        )
    )
}

expect inline fun <reified Entity> createDataStore(
    defaultValue: Entity,
    serializer: KSerializer<Entity>,
    pathProducer: DataStorePathProducer
): DataStore<Entity>
