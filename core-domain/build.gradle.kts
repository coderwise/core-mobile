plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)

    id("maven-publish")
}

kotlin {
    androidLibrary {
        namespace = "com.coderwise.core.domain"
        compileSdk = libs.versions.compileSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()

        @Suppress("UnstableApiUsage")
        withHostTestBuilder {
        }
    }

    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach {
        it.binaries.framework {
            baseName = "core-domainKit"
            isStatic = true
        }
    }

    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }

        androidMain.dependencies {}
        iosMain.dependencies {}
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}

group = "com.coderwise.core.domain"
version = libs.versions.coderwiseCore.get()
