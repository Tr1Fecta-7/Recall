import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("de.mannodermaus.android-junit5")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
}

android {
    namespace = "${ProjectConfiguration.APPLICATION_ID}.data"
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

    libraryVariants.configureEach {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
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
    implementation("io.insert-koin:koin-annotations:${Versions.KOIN_KSP}")
    ksp("io.insert-koin:koin-ksp-compiler:${Versions.KOIN_KSP}")

    // Networking
    implementation("io.ktor:ktor-client-okhttp:${Versions.KTOR}")
    implementation("io.ktor:ktor-client-serialization:${Versions.KTOR}")
    implementation("io.ktor:ktor-client-logging-jvm:${Versions.KTOR}")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLIN_SERIALIZATION}")

    // Room Database
    ksp("androidx.room:room-compiler:${Versions.DATABASE}")
    implementation("androidx.room:room-runtime:${Versions.DATABASE}")
    implementation("androidx.room:room-ktx:${Versions.DATABASE}")
    annotationProcessor("androidx.room:room-compiler:${Versions.DATABASE}")
}
