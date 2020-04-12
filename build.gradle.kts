import java.net.URI

plugins {
    kotlin("jvm") version "1.3.61"
    id("com.github.johnrengelman.shadow") version "5.1.0"
}

group = "pl.serwermc"
version = "1.0"

repositories {
    mavenCentral()
    jcenter()
    maven { url = URI("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = URI("https://oss.sonatype.org/content/repositories/snapshots") }
}

sourceSets {
    main {
        java.setSrcDirs(listOf("src"))
        resources.setSrcDirs(listOf("res"))
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.koin:koin-core:2.0.1")
    implementation("org.koin:koin-core-ext:2.0.1")
    compileOnly("org.spigotmc:spigot-api:1.15.2-R0.1-SNAPSHOT")
    implementation("org.jetbrains.exposed", "exposed-core", "0.23.1")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.23.1")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.23.1")
    implementation("mysql:mysql-connector-java:8.0.19")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.10.2")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation("org.slf4j:slf4j-api:1.7.30")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}