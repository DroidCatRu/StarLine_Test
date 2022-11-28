plugins {
    id("starline.android.library")
    id("starline.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "ru.droidcat.starline.core.server"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
}
