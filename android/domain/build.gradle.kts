plugins {
    id("kotlin")
    id("org.jetbrains.kotlin.jvm")
    id("com.google.devtools.ksp")
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = Versions.JVM.toString()
}

tasks.compileTestKotlin {
    kotlinOptions.jvmTarget = Versions.JVM.toString()
}

java {
    sourceCompatibility = Versions.JVM
    targetCompatibility = Versions.JVM
}

kotlin.sourceSets["main"].kotlin {
    srcDir("build/generated/ksp/main/kotlin")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = Versions.JVM.toString()
    }
}

dependencies {
    // Koin DI
    implementation("io.insert-koin:koin-core:${Versions.KOIN}")
    implementation("io.insert-koin:koin-annotations:${Versions.KOIN_KSP}")
    ksp("io.insert-koin:koin-ksp-compiler:${Versions.KOIN_KSP}")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}")
}