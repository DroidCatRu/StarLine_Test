plugins {
    id("starline.android.feature")
    id("starline.android.library.compose")
    id("starline.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "ru.droidcat.starline.feature.carsonmap"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))
    implementation(project(":core:map"))

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.yandex.maps)
}
