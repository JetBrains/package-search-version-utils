@file:Suppress("UnstableApiUsage")

rootProject.name = "packagesearch-version-utils"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://packages.jetbrains.team/maven/p/kpm/public")
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.jetbrains.packagesearch.build-config") {
                useModule("org.jetbrains.packagesearch:packagesearch-build-config:${requested.version}")
            }
        }
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://packages.jetbrains.team/maven/p/kpm/public")
    }
    versionCatalogs {
        create("packageSearchCatalog") {
            if (file("../packagesearch-version-catalog/packagesearch.versions.toml").exists())
                from(files("../packagesearch-version-catalog/packagesearch.versions.toml"))
            else from("org.jetbrains.packagesearch:packagesearch-version-catalog:1.0.0")
        }
    }
}