buildscript {
    dependencies {
        classpath("de.mannodermaus.gradle.plugins:android-junit5:${Versions.ANDROID_JUNIT5}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.KOTLIN}")
    }
}
plugins {
    id("com.android.application") version Versions.ANDROID_GRADLE apply false
    id("com.android.library") version Versions.ANDROID_GRADLE apply false
    id("org.jetbrains.kotlin.android") version Versions.KOTLIN apply false
    id("com.google.devtools.ksp") version Versions.KSP apply false
}

allprojects {

    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
