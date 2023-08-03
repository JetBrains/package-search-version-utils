# DEPRECATED
This library has been moved [here](https://github.com/JetBrains/package-search-api-models/tree/master/version-utils)!

# Package Search Version Utils [![obsolete JetBrains project](https://jb.gg/badges/obsolete.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)

A collection of utils related to package versions.

For now it only contains logic to detect if a package version name may be considered stable or not.

## Usage

```kotlin
repositories {
    maven("https://packages.jetbrains.team/maven/p/kpm/public")
}
```      

Then add the dependency:

```kotlin
dependencies {
    implementation("org.jetbrains.packagesearch:pkgs-version-utils:[version]")
}
```
