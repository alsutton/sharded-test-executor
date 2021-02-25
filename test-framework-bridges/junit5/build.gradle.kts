import net.ltgt.gradle.errorprone.errorprone

plugins {
    java
    id("net.ltgt.errorprone")
}

repositories {
    mavenCentral()
}

val junitVersion="5.7.0"
dependencies {
    implementation(project(":lib:core"))
    implementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")

    errorprone("com.google.errorprone:error_prone_core:2.5.1")
}

tasks.withType<JavaCompile>().configureEach {
    options.errorprone.allErrorsAsWarnings.set(false)
}