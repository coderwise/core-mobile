package com.coderwise.core.data.utils

import okio.Path
import okio.Path.Companion.toPath

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DataStorePathProducer {
    actual fun producePath(fileName: String): Path {
//        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
//            directory = NSDocumentDirectory,
//            inDomain = NSUserDomainMask,
//            appropriateForURL = null,
//            create = false,
//            error = null,
//        )
//        return (documentDirectory!!.path + "/$fileName.pb").toPath()
        TODO("Not yet implemented")
    }
}
