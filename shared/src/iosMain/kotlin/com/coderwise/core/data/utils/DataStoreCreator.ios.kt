package com.coderwise.core.data.utils

import androidx.datastore.core.DataStore
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.KSerializer
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask


@OptIn(ExperimentalForeignApi::class)
inline fun <reified Entity> createDataStore(
    defaultValue: Entity,
    serializer: KSerializer<Entity>
): DataStore<Entity> = DataStoreCreator.create(
    defaultValue = defaultValue,
    serializer = serializer,
    fileSystem = FileSystem.SYSTEM,
    producePath = {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        val fileName = Entity::class.simpleName
        (documentDirectory!!.path + "/$fileName.pb").toPath()
    }
)

actual inline fun <reified Entity> createDataStore(
    defaultValue: Entity,
    serializer: KSerializer<Entity>,
    pathProducer: DataStorePathProducer
) = DataStoreCreator.create(
    defaultValue = defaultValue,
    serializer = serializer,
    fileSystem = FileSystem.SYSTEM,
    producePath = { pathProducer.producePath(Entity::class.simpleName!!) }
)