plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)

    id("maven-publish")
}

kotlin {
    androidLibrary {
        namespace = "com.coderwise.core.time"
        compileSdk = libs.versions.compileSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()

        withHostTestBuilder {
        }
    }

    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach {
        it.binaries.framework {
            baseName = "core-timeKit"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)

            // kotlinx DateTime
            implementation(libs.kotlinx.datetime)

            // koin
            implementation(libs.koin.core)
        }

        commonTest.dependencies {
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}

group = "com.coderwise.core.time"
version = "1.0"