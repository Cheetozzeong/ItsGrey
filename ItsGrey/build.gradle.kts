buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.10")
    }
    repositories{
        google()
    }
}
plugins {
    id("com.android.application") version "7.4.1" apply false
    id("com.android.library") version "7.4.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.0" apply false
    id("com.google.dagger.hilt.android") version Versions.HILT apply false
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}