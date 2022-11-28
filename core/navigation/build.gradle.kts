plugins {
    id("starline.android.library")
    id("starline.android.library.compose")
    id("starline.android.hilt")
}

android {
    namespace = "ru.droidcat.starline.core.navigation"
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material3)
}
