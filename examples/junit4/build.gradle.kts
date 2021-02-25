import net.ltgt.gradle.errorprone.errorprone
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    java
}

repositories {
    mavenCentral()
}

val junitVersion="4.13.2"
dependencies {
    testImplementation("junit:junit:${junitVersion}")
    testImplementation(project(":test-framework-bridges:junit4"))

    testImplementation(project(":lib:core")) // Not needed unless you're accessing the shard ID.
}

tasks.test {
    useJUnit()
    this.testLogging {
        this.showStandardStreams = true;
    }
}