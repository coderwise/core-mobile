package com.coderwise.core.versioning

import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import com.android.build.gradle.internal.crash.afterEvaluate
import com.coderwise.core.versioning.utils.addPrintOutputAction
import com.coderwise.core.versioning.utils.addRenameBundleAction
import com.coderwise.core.versioning.utils.addRenameMappingAction
import com.coderwise.core.versioning.utils.android
import com.coderwise.core.versioning.utils.archivesBaseName
import com.coderwise.core.versioning.utils.generateOutputName
import com.coderwise.core.versioning.utils.getAPKPath
import com.coderwise.core.versioning.utils.getBundlePath
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.util.Locale

class VersioningPlugin : Plugin<Project> {
    private val bundleRegex = "^(:.*:)*bundle.*".toRegex()
    private val assembleRegex = "^(:.*:)*assemble.*".toRegex()

    override fun apply(project: Project) {
        val ext = project.extensions.create("versioning", VersioningPluginExtension::class.java)

        project.tasks.register("printVersions") {
            it.group = "versioning"
            it.description = "Print versions"
            it.doLast {
                ext.getVersionName()
                ext.getVersionCode()
            }
        }

        project.pluginManager.withPlugin("com.android.application") {
            val android = project.android()

            android.applicationVariants.configureEach { variant ->
                val baseName = project.archivesBaseName()

                variant.outputs.configureEach {
                    val newApkName = variant.generateOutputName(baseName, "apk")
                    (it as BaseVariantOutputImpl).outputFileName = newApkName
                }
            }

            afterEvaluate {
                val baseName = project.archivesBaseName()
                project.tasks.configureEach { task ->
                    if (task.name.matches(bundleRegex)) {
                        val variantName =
                            task.name.substringAfter("bundle").decapitalize(Locale.ROOT)

                        android.applicationVariants.configureEach { variant ->
                            if (variant.name != variantName) {
                                val bundleName = "$baseName-${variant.baseName}.aab"
                                val newBundleName = variant.generateOutputName(baseName, "aab")
                                val bundleOutputPath = variant.getBundlePath(project.buildDir)

                                task.addRenameBundleAction(
                                    bundleName,
                                    newBundleName,
                                    bundleOutputPath,
                                    false
                                )

                                variant.addRenameMappingAction(task)
                            }
                        }
                    } else if (task.name.matches(assembleRegex)) {
                        val variantName =
                            task.name.substringAfter("assemble").decapitalize(Locale.ROOT)
                        android.applicationVariants.configureEach { variant ->
                            if (variant.name == variantName) {
                                variant.outputs.configureEach {
                                    val apkName = (it as BaseVariantOutputImpl).outputFileName
                                    val apkOutputPath = variant.getAPKPath(project.buildDir)
                                    task.addPrintOutputAction(apkOutputPath, apkName)
                                }

                                variant.addRenameMappingAction(task)
                            }
                        }
                    }
                }
            }
        }
    }
}