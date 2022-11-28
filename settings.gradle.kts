pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "StarLine Test"

include(":app")
include(":core:ui")
include(":core:network")
include(":feature:carsonmap")
include(":core:navigation")
include(":core:map")
include(":core:domain")
