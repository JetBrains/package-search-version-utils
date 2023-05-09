@file:Suppress("UNUSED_VARIABLE")

plugins {
    alias(packageSearchCatalog.plugins.kotlin.multiplatform)
    alias(packageSearchCatalog.plugins.kotlin.plugin.serialization)
    alias(packageSearchCatalog.plugins.detekt)
    alias(packageSearchCatalog.plugins.kotlinter)
    alias(packageSearchCatalog.plugins.packagesearch.build.config)
    `maven-publish`
}

kotlin {
    jvm()
    js(IR) {
        browser()
        nodejs()
    }
    ios()
    macosArm64()
    macosX64()
    watchos()
    tvos()

    sourceSets {
        commonMain {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(packageSearchCatalog.kotlinx.datetime)
                implementation(packageSearchCatalog.kotlinx.serialization.core)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(npm("date-fns", "2.30.0"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(packageSearchCatalog.junit.jupiter.api)
                implementation(packageSearchCatalog.junit.jupiter.params)
                implementation(packageSearchCatalog.assertk)
                runtimeOnly(packageSearchCatalog.junit.jupiter.engine)
            }
        }
        val appleMain by creating {
            dependsOn(commonMain.get())
        }
        val watchosMain by getting {
            dependsOn(appleMain)
        }
        val iosMain by getting {
            dependsOn(appleMain)
        }
        val macosMain by creating {
            dependsOn(appleMain)
        }
        val macosArm64Main by getting {
            dependsOn(macosMain)
        }
        val macosX64Main by getting {
            dependsOn(macosMain)
        }
        val tvosMain by getting {
            dependsOn(appleMain)
        }
    }
}

group = "org.jetbrains.packagesearch"
version = System.getenv("GITHUB_REF")?.substringAfterLast("/") ?: "2.5.0"

dependencies {
    detektPlugins(packageSearchCatalog.logback.classic)
    detektPlugins(packageSearchCatalog.detekt.formatting)
}

publishing {
    publications {
        withType<MavenPublication> {
            pom {
                name.set("Package Search - Version Utils")
                description.set("Utility to compare versions in Package Search")
                url.set("https://package-search.jetbrains.com/")
                scm {
                    connection.set("scm:https://github.com/JetBrains/package-search-version-utils.git")
                    developerConnection.set("scm:https://github.com/JetBrains/package-search-version-utils.git")
                    url.set("https://github.com/JetBrains/package-search-version-utils")
                }
            }
        }
    }
    repositories {
        maven {
            name = "Space"
            setUrl("https://packages.jetbrains.team/maven/p/kpm/public")
            credentials {
                username = System.getenv("MAVEN_SPACE_USERNAME")
                password = System.getenv("MAVEN_SPACE_PASSWORD")
            }
        }
    }
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
}