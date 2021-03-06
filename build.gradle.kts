plugins {
    val kotlinVersion = "1.6.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    `maven-publish`
    id("io.gitlab.arturbosch.detekt") version "1.20.0"
    id("org.jmailen.kotlinter") version "3.10.0"
}

group = "org.jetbrains.packagesearch"
version = System.getenv("GITHUB_REF")?.substringAfterLast("/") ?: "2.5.0"

dependencies {
    detektPlugins("ch.qos.logback:logback-classic:1.2.11")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.20.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

kotlin {
    target {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
}

java {
    targetCompatibility = JavaVersion.VERSION_1_8
    sourceCompatibility = JavaVersion.VERSION_1_8
}

detekt {
    toolVersion = "1.20.0"
    autoCorrect = !isCi
    source = files("src/main/java", "src/main/kotlin")
    config = files("detekt.yml")
    buildUponDefaultConfig = true
}

kotlinter {
    reporters = arrayOf("html", "checkstyle", "plain")
}

val sourcesJar by tasks.registering(Jar::class) {
    group = "publishing"
    from(kotlin.sourceSets.main.get().kotlin.sourceDirectories)
    archiveClassifier.set("sources")
    destinationDirectory.set(buildDir.resolve("artifacts"))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(sourcesJar)

            version = project.version.toString()
            groupId = group.toString()
            artifactId = project.name

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

val isCi
    get() = System.getenv("CI") != null || System.getenv("CONTINUOUS_INTEGRATION") != null

tasks {
    test {
        useJUnitPlatform()
    }
}
