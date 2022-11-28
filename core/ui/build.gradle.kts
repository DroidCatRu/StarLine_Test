plugins {
    id("starline.android.library")
    id("starline.android.library.compose")
}

android {
    namespace = "ru.droidcat.starline.core.ui"
}

dependencies {
    implementation(libs.androidx.core.ktx)

    api(libs.accompanist.placeholder)
    api(libs.accompanist.swiperefresh)
    api(libs.android.material)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    debugApi(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.runtime.livedata)
    api(libs.androidx.metrics)
    api(libs.androidx.tracing.ktx)
}
