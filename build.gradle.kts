plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kotlinAndroid) apply false

    alias(libs.plugins.kotlinMultiplatformLibrary) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
}
