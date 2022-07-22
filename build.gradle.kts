import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
}
repositories {
    mavenCentral()
}
dependencies {
    testImplementation(kotlin("test"))
}
tasks {
    test {
        useJUnitPlatform()
    }
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "18"
    }
}
group = "com.vogel.sjoerd"
version = "1.0.0-SNAPSHOT"
description = "sudokus"
