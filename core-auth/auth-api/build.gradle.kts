import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)
    alias(libs.plugins.kotlinSerialization)

    alias(libs.plugins.mavenPublish)
}

kotlin {
    androidLibrary {
        namespace = "com.coderwise.core.auth.api"
        compileSdk = libs.versions.compileSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
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

    coordinates(group.toString(), "auth-api", version.toString())

    pom {
        name = "Core Auth API library"
        description = "Core Auth API library"
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