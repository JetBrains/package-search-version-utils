# Package Search Version Utils

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
