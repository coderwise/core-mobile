package com.coderwise.core.versioning.utils

import org.gradle.api.Task
import java.io.File

internal fun Task.addRenameBundleAction(
    oldOutput: String,
    newOutput: String,
    outputPath: String,
    keepOriginal: Boolean
) {
    println(newOutput)

    doLast {
        val newFile = File(outputPath + newOutput)
        val oldFile = File(outputPath + oldOutput)

        if (oldFile.exists()) {
            oldFile.copyTo(newFile, overwrite = true)
            if (newFile.exists()) {
                if (!keepOriginal) oldFile.delete()
                logger.debug("Created bundle file://${newFile.absolutePath}")
            } else {
                logger.error("Creating $newOutput from $oldOutput failed.")
            }
        } else {
            if (newFile.exists()) {
                logger.debug("Cached output $oldOutput was already renamed. Set 'keepOriginalBundleFile' if you like to keep those files.")
            } else {
                logger.error("$oldOutput and $newOutput not found. Try a clean build.")
            }
        }
    }
}

internal fun Task.addRenameMappingAction(
    oldOutput: File,
    newOutput: String
) {
    println(newOutput)

    doLast {
        val newFile = File(oldOutput.absolutePath.replaceAfterLast("/", newOutput))

        if (oldOutput.exists()) {
            oldOutput.copyTo(newFile, overwrite = true)
            if (newFile.exists()) {
                oldOutput.delete()
                logger.debug("Created mapping $newOutput")
            } else {
                logger.error("Creating $newOutput from mapping.txt failed.")
            }
        } else {
            if (newFile.exists()) {
                logger.debug("Cached output mapping.txt was already renamed. Set 'keepOriginalMappingFile' if you like to keep those files.")
            } else {
                logger.error("mapping.txt and $newOutput not found. Try a clean build.")
            }
        }
    }
}

internal fun Task.addPrintOutputAction(filepath: String, filename: String) {
    logger.debug(filename)

    doLast {
        if (File(filepath).exists()) logger.debug("Created file://${filepath + filename}")
    }
}