import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
}

group = "uks.master.thesis"
version = "0.1.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.masterchi3f:master-iac:0.3.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}
