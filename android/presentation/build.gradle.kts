plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("de.mannodermaus.android-junit5")
}

android {
    namespace = "${ProjectConfiguration.APPLICATION_ID}.presentation"
    compileSdk = Versions.COMPILE_SDK

    defaultConfig {
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK
    }

    compileOptions {
        sourceCompatibility = Versions.JVM
        targetCompatibility = Versions.JVM
    }
    
    kotlinOptions {
        jvmTarget = Versions.JVM.toString()
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.KOTLIN_COMPILER_EXTENSION
    }

    libraryVariants.configureEach {
        kotlin.sourceSets {
            getByName(name) { kotlin.srcDir("build/generated/ksp/$name/kotlin") }
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = Versions.JVM.toString()
    }
}

dependencies {
    implementation(project(":domain"))

    // Koin DI
    implementation("io.insert-koin:koin-core:${Versions.KOIN}")
    implementation("io.insert-koin:koin-android:${Versions.KOIN}")
    implementation("io.insert-koin:koin-annotations:${Versions.KOIN_KSP}")
    ksp("io.insert-koin:koin-ksp-compiler:${Versions.KOIN_KSP}")

    // Android
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE_KTX}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_KTX}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE_KTX}")
}
