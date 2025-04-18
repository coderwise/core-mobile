plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinSerialization)

    id("java-library")
    id("maven-publish")
}

dependencies {
    implementation(project(":core-auth:auth-data"))

    implementation(libs.ktor.server.core)

    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = project.name
            from(components["kotlin"])
        }
    }
    repositories {
        mavenLocal()
    }
}

group = "com.coderwise.core.auth"
version = libs.versions.coderwiseCore.get()
