package com.coderwise.core.versioning.utils

import org.gradle.api.GradleException
import java.util.concurrent.TimeUnit

internal fun String.runCommand(): String {
    return try {
        ProcessBuilder(this.split("\\s".toRegex())).start().run {
            waitFor(10, TimeUnit.SECONDS)
            inputStream.bufferedReader().use { it.readText().trim() }
        }
    } catch (e: Exception) {
        throw GradleException("Failed to execute command: $this", e)
    }
}