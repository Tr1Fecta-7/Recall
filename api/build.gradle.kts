import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.5"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
	kotlin("plugin.jpa") version "1.7.22"
}

group = "com.recall"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web") {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
	}
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("com.mysql:mysql-connector-j")
}

springBoot {
	mainClass.set("com.recall.api.ApiApplicationKt")
}

tasks.withType<Jar> {
	manifest {
		attributes["Main-Class"] = "com.recall.api.ApiApplicationKt"
	}

	// To avoid the duplicate handling strategy error
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE

	// To add all of the dependencies
	from(sourceSets.main.get().output)

	dependsOn(configurations.runtimeClasspath)
	from({
		configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
	})
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
