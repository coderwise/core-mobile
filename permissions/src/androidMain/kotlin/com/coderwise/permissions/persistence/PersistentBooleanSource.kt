package com.coderwise.permissions.persistence

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.File

class PersistentBooleanSource(
    context: Context,
    scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    private val persisted: Persisted = Persisted("denied.txt", context, scope)

    fun read(): Boolean = persisted.value

    fun write(value: Boolean) {
        persisted.write(value)
    }
}

class Persisted(
    private val id: String,
    private val context: Context,
    private val scope: CoroutineScope
) {
    var value: Boolean = read()

    private fun read(): Boolean {
        val file = File(context.filesDir, id)
        if (!file.exists()) {
            file.createNewFile()
        }
        val inputStream = context.openFileInput(id)
        val bytes = inputStream.readBytes()
        inputStream.close()
        return String(bytes).toBoolean()
    }

    fun write(value: Boolean) {
        if (this.value != value) {
            val outputStream = context.openFileOutput(id, Context.MODE_PRIVATE)
            outputStream.write(value.toString().toByteArray())
            outputStream.close()
        }
        this.value = value
    }
}