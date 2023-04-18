plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    kotlin("kapt")
}

android {
    flavorDimensions += "default"
    namespace = ProjectConfiguration.APPLICATION_ID
    compileSdk = Versions.COMPILE_SDK

    defaultConfig {
        applicationId = ProjectConfiguration.APPLICATION_ID
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK
        versionCode = ProjectConfiguration.VERSION_CODE
        versionName = ProjectConfiguration.VERSION_NAME
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.KOTLIN_COMPILER_EXTENSION
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }

    compileOptions {
        sourceCompatibility = Versions.JVM
        targetCompatibility = Versions.JVM
    }

    kotlinOptions {
        freeCompilerArgs = listOf(
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
        )
        jvmTarget = Versions.JVM.toString()
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = Versions.JVM.toString()
    }
}

dependencies {
    // Project modules
    implementation(project(":presentation"))
    implementation(project(":domain"))
    implementation(project(":data"))

    // Android
    implementation("androidx.core:core-ktx:${Versions.CORE_KTX}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_KTX}")
    implementation("androidx.appcompat:appcompat:${Versions.APP_COMPAT}")
    implementation("androidx.core:core-splashscreen:${Versions.SPLASH}")

    // Compose - Navigation
    implementation("io.github.raamcosta.compose-destinations:core:${Versions.DIRECTIONS}")
    implementation("io.github.raamcosta.compose-destinations:animations-core:${Versions.DIRECTIONS}")
    ksp("io.github.raamcosta.compose-destinations:ksp:${Versions.DIRECTIONS}")

    // Compose
    implementation("androidx.activity:activity-compose:${Versions.ACTIVITY_COMPOSE}")
    implementation("androidx.compose.ui:ui:${Versions.COMPOSE}")
    implementation("androidx.compose.ui:ui-util:${Versions.COMPOSE}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE}")
    implementation("androidx.compose.material:material:${Versions.COMPOSE}")
    debugImplementation("androidx.compose.ui:ui-tooling:${Versions.COMPOSE}")

    // Kotlin immutable
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:${Versions.KOTLIN_IMMUTABLE}")

    // Koin DI
    implementation("io.insert-koin:koin-android:${Versions.KOIN}")
    implementation("io.insert-koin:koin-androidx-navigation:${Versions.KOIN}")
    implementation("io.insert-koin:koin-androidx-compose:${Versions.KOIN_COMPOSE}")
    implementation("io.insert-koin:koin-annotations:${Versions.KOIN_KSP}")
    ksp("io.insert-koin:koin-ksp-compiler:${Versions.KOIN_KSP}")

    // Coil
    implementation("io.coil-kt:coil:${Versions.COIL}")
    implementation("io.coil-kt:coil-compose:${Versions.COIL}")
}
