pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "More"
include(":app")
include(":core")
include(":core:data")
include(":core:domain")
include(":core:presentation")
include(":features")
include(":features:home")
include(":features:login")
include(":core:datastore")
include(":core:protodatastore")
