import com.adarshr.gradle.testlogger.theme.ThemeType
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    `java-library`
    `maven-publish`
    id("com.vanniktech.maven.publish") version "0.30.0"
    id("com.adarshr.test-logger") version "4.0.0"
    id("com.mikepenz.aboutlibraries.plugin") version "11.4.0-b01"
}

group="com.yanchware"

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation(libs.ch.qos.logback.logback.classic)
    implementation(libs.org.apache.commons.commons.lang3)
    implementation(libs.com.fasterxml.jackson.core.jackson.core)
    implementation(libs.com.fasterxml.jackson.core.jackson.databind)
    implementation(libs.com.fasterxml.jackson.core.jackson.annotations)
    implementation(libs.com.fasterxml.jackson.datatype.jackson.datatype.jsr310)
    implementation(libs.io.github.resilience4j.resilience4j.core)
    implementation(libs.io.github.resilience4j.resilience4j.retry)
    implementation(libs.org.slf4j.slf4j.api)
    testImplementation(libs.com.jayway.jsonpath.json.path)
    testImplementation(libs.org.junit.jupiter.junit.jupiter.api)
    testImplementation(libs.org.junit.jupiter.junit.jupiter.engine)
    testImplementation(libs.org.junit.pioneer.junit.pioneer)
    testImplementation(libs.org.junit.jupiter.junit.jupiter.params)
    testImplementation(libs.org.wiremock.wiremock)
    testImplementation(libs.org.assertj.assertj.core)
    testImplementation(libs.com.flextrade.jfixture.jfixture)
    testImplementation(libs.org.mockito.mockito.core)
    compileOnly(libs.org.projectlombok.lombok)
    annotationProcessor(libs.org.projectlombok.lombok)
    testCompileOnly(libs.org.projectlombok.lombok)
    testAnnotationProcessor(libs.org.projectlombok.lombok)
}

group = "com.yanchware"
description = "com.yanchware:fractal.sdk"
java.sourceCompatibility = JavaVersion.VERSION_21

mavenPublishing {
    coordinates("com.yanchware", "fractal.sdk")
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
    signAllPublications()

    pom {
        name = "Fractal Java SDK"
        description = "Java SDK to create, update and invoke fractals"
        url = "https://fractal.cloud"
        licenses {
            license {
                name = "GNU GENERAL PUBLIC LICENSE, Version 3.0"
                url = "https://www.gnu.org/licenses/gpl-3.0.en.html"
            }
        }
        developers {
            developer {
                id.set("yanchware")
                name.set("YanchWare")
                url.set("https://github.com/YanchWare")
            }
        }
        scm {
            connection = "scm:git:git://git@github.com:YanchWare/fractal-java-sdk.git"
            developerConnection = "scm:git:ssh://git@github.com:YanchWare/fractal-java-sdk.git"
            url = "https://github.com/YanchWare/fractal-java-sdk"
        }
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
    (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    (options as StandardJavadocDocletOptions).tags("important.note")
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use JUnit Jupiter test framework
            useJUnitJupiter("5.10.3")
        }
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    maxHeapSize = "1G"
    testlogger {
        theme = ThemeType.MOCHA
        showFullStackTraces = true
    }
}
