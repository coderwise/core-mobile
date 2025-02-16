import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)

    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)

    id("maven-publish")
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
        publishAllLibraryVariants()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui)
            implementation(libs.compose.material3)
            implementation(libs.androidx.ui.tooling.preview.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.lifecycle.viewmodel.compose)

            // datastore
            implementation(libs.androidx.datastore)
            implementation(libs.androidx.datastore.core.okio)
            implementation(libs.kotlinx.serialization.protobuf)

            implementation(libs.androidx.datastore.preferences)
        }
    }
}

android {
    namespace = "com.coderwise.core"
    compileSdk = 35
    defaultConfig {
        minSdk = 28
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
}

publishing {
    repositories {
        mavenLocal()
    }
}

group = "com.coderwise.core"
version = "1.0"
