import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)

    alias(libs.plugins.kotlinSerialization)

    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)

    alias(libs.plugins.mavenPublish)
}

kotlin {
    androidLibrary {
        namespace = "com.coderwise.core.auth.ui"
        compileSdk = libs.versions.compileSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core-ui"))
            implementation(project(":core-domain"))
            implementation(project(":core-auth:auth-domain"))

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.navigation.compose)

            implementation(libs.kotlinx.serialization.protobuf)
        }
    }
}

group = "com.coderwise.core"
version = libs.versions.coderwiseCore.get()

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    if (project.hasProperty("sign")) {
        signAllPublications()
    }

    coordinates(group.toString(), "auth-ui", version.toString())

    pom {
        name = "Core Auth UI library"
        description = "Core Auth UI library"
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