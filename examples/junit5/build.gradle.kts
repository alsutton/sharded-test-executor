import net.ltgt.gradle.errorprone.errorprone
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    java
}

repositories {
    mavenCentral()
}

val junitVersion="5.7.0"
dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testImplementation(project(":test-framework-bridges:junit5"))

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
    testImplementation(project(":lib:core")) // Not needed unless you're accessing the shard ID.
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs("-Djunit.jupiter.extensions.autodetection.enabled=true")
    this.testLogging {
        this.showStandardStreams = true;
    }
}