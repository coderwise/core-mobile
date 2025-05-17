import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)

    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)

    alias(libs.plugins.mavenPublish)
}

kotlin {
    androidLibrary {
        namespace = "com.coderwise.core.permissions"
        compileSdk = libs.versions.compileSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()

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
version = libs.versions.coderwiseCore.get()

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    if (project.hasProperty("sign")) {
        signAllPublications()
    }

    coordinates(group.toString(), "core-permissions", version.toString())

    pom {
        name = "Core Permissions library"
        description = "Core Permissions library"
        inceptionYear = "2025"
        url = "https://github.com/coderwise/core.mobile"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "coderwise"
                name = "Coderwise"
                url = "https://github.com/coderwise"
            }
        }
        scm {
            url = "https://github.com/coderwise/core.mobile"
            connection = "https://github.com/coderwise/core.mobile.git"
            developerConnection = "git://github.com/coderwise/core.mobile.git"
        }
    }
}