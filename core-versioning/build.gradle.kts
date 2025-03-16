plugins {
    alias(libs.plugins.jetbrainsKotlinJvm)
    id("java-gradle-plugin")
    id("maven-publish")
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

group = "com.coderwise.core"
version = "1.0"

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.gradle)
}

gradlePlugin {
    plugins {
        create("versioningPlugin") {
            id = "com.coderwise.core.versioning"
            implementationClass = "com.coderwise.core.versioning.VersioningPlugin"
        }
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}
