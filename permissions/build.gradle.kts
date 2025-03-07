plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)

    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)

    id("maven-publish")
}

kotlin {
    androidLibrary {
        namespace = "com.coderwise.core.permissions"
        compileSdk = 35
        minSdk = 24

        @Suppress("UnstableApiUsage")
        withHostTestBuilder {
        }
    }

    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach {
        it.binaries.framework {
            baseName = "permissionsKit"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)

            implementation(compose.runtime)

            // koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }

        iosMain.dependencies {
        }

        getByName("androidHostTest") {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.mockk)
            }
        }
    }
}

group = "com.coderwise.core.permissions"
version = "1.0"
