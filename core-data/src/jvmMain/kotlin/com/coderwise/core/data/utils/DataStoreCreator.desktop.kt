package com.coderwise.core.data.utils

import okio.Path
import okio.Path.Companion.toPath

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DataStorePathProducer {
    actual fun producePath(fileName: String): Path =
        (System.getProperty("user.home") + "/$fileName.pb").toPath()
}
