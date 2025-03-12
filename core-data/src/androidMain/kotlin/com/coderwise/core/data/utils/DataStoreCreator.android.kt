package com.coderwise.core.data.utils

import android.content.Context
import androidx.datastore.dataStoreFile
import okio.Path
import okio.Path.Companion.toOkioPath

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DataStorePathProducer(
    private val context: Context
) {
    actual fun producePath(fileName: String): Path =
        context.dataStoreFile("$fileName.pb").toOkioPath()
}
