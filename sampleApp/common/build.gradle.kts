plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)
    alias(libs.plugins.kotlinSerialization)

    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    android {
        namespace = "com.coderwise.core.sample"
        compileSdk = libs.versions.compileSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()
        androidResources.enable = true
    }

    //noinspection WrongGradleMethod
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach {
        it.binaries.framework {
            baseName = "composeAppKit"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core-ui"))
            implementation(project(":core-domain"))
            implementation(project(":core-data"))
            implementation(project(":core-permissions"))
            implementation(project(":core-location"))
            implementation(project(":core-time"))

            implementation(project(":core-auth:auth-domain"))
            implementation(project(":core-auth:auth-ui"))
            implementation(project(":core-auth:auth-data"))
            implementation(project(":sampleApp:server-api"))

            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.material3.adaptive.navigation.suite)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.ui.tooling.preview)

            implementation(libs.navigation.compose)

            // koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // date-time library
            implementation(libs.kotlinx.datetime)

            // datastore
            implementation(libs.kotlinx.serialization.protobuf)

            // ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.auth)
        }

        androidMain.dependencies {
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}
