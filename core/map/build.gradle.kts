plugins {
    id("starline.android.library")
    id("starline.android.library.compose")
    id("starline.android.hilt")
    alias(libs.plugins.secrets)
}

android {
    namespace = "ru.droidcat.starline.core.map"

    buildFeatures {
        buildConfig = true
    }
}

secrets {
    defaultPropertiesFileName = "local.defaults.properties"
}

dependencies {
    implementation(project(":core:ui"))
    implementation(libs.yandex.maps)
    implementation(libs.androidx.hilt.navigation.compose)
}
