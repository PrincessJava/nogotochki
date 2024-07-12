plugins {
    kotlin("jvm") version "2.0.0"
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.liquibase.gradle") version "2.2.2"
}

group = "ru.riton"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.0.0")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.telegram:telegrambots:6.9.7.1")
    implementation("org.telegram:telegrambots-spring-boot-starter:6.9.7.1")
    implementation("org.telegram:telegrambotsextensions:6.9.7.1")
//    implementation("org.telegram:telegrambots-meta:7.7.0")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}