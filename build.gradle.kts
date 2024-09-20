import org.liquibase.gradle.OutputEnablingLiquibaseRunner.main

val springBootVersion = "3.3.1"

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
    //spring
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.0.0")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")

    //database
    implementation("org.postgresql:postgresql:42.7.3")
    liquibaseRuntime("org.postgresql:postgresql")

    implementation("org.liquibase:liquibase-core:4.29.2")
    liquibaseRuntime("org.liquibase:liquibase-core")
    liquibaseRuntime("org.liquibase.ext:liquibase-hibernate5:4.27.0")
    liquibaseRuntime("org.springframework.boot:spring-boot:$springBootVersion")

    //telegram
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