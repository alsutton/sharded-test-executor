import net.ltgt.gradle.errorprone.errorprone

plugins {
    java
    id("net.ltgt.errorprone")
}

repositories {
    mavenCentral()
}

val junitVersion="4.13.2"
dependencies {
    implementation(project(":lib:core"))
    implementation("junit:junit:${junitVersion}")

    errorprone("com.google.errorprone:error_prone_core:2.5.1")
}

tasks.withType<JavaCompile>().configureEach {
    options.errorprone.allErrorsAsWarnings.set(false)
}