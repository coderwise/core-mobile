package com.coderwise.core.data.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import kotlinx.serialization.KSerializer
import okio.FileSystem
import okio.Path.Companion.toOkioPath

public inline fun <reified Entity> createDataStore(
    context: Context,
    defaultValue: Entity,
    serializer: KSerializer<Entity>
): DataStore<Entity> = DataStoreCreator.create(
    defaultValue = defaultValue,
    serializer = serializer,
    fileSystem = FileSystem.SYSTEM,
    producePath = {
        context.dataStoreFile("${Entity::class.java.simpleName}.pb").toOkioPath()
    }
)