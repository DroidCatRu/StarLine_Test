plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.ktLint) apply false
    alias(libs.plugins.android.library) apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
}

allprojects {
    apply(
        plugin = "io.gitlab.arturbosch.detekt"
    )

    detekt {
        buildUponDefaultConfig = true
        config = files("$rootDir/gradle/detekt.yml")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
