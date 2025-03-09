plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)

    id("maven-publish")
}

kotlin {
    androidLibrary {
        namespace = "com.coderwise.core.domain"
        compileSdk = 35
        minSdk = 24

        withHostTestBuilder {
        }
    }

    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach {
        it.binaries.framework {
            baseName = "core-domainKit"
            isStatic = true
        }
    }
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
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
version = "1.0"
