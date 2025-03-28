plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)
    alias(libs.plugins.kotlinSerialization)

    id("maven-publish")
}

kotlin {

    androidLibrary {
        namespace = "com.coderwise.core.data"
        compileSdk = libs.versions.compileSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()

        @Suppress("UnstableApiUsage")
        withHostTestBuilder {
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core-domain"))

            implementation(libs.kotlin.stdlib)
            implementation(libs.kotlinx.coroutines.core)

            // datastore
            api(libs.androidx.datastore)
            implementation(libs.androidx.datastore.core.okio)
            implementation(libs.kotlinx.serialization.protobuf)

            // koin
            implementation(libs.koin.core)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }

        androidMain.dependencies {
        }

        iosMain.dependencies {
        }
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}

group = "com.coderwise.core.data"
version = libs.versions.coderwiseCore.get()
