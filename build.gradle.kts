import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `maven-publish`
    `java-library`
    signing
    kotlin("jvm") version "2.2.21"
    id("org.jetbrains.kotlinx.kover") version "0.8.3"
    id("pl.allegro.tech.build.axion-release") version "1.18.9"
}

scmVersion {
    versionIncrementer("incrementPatch")
    tag {
        prefix = ""
    }
}

group = "com.mckernant1"
project.version = scmVersion.version

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

    // Logging
    implementation("org.slf4j:slf4j-api:2.0.12")

    // Common
    implementation("org.apache.commons:commons-lang3:3.19.0")
    implementation("org.apache.commons:commons-collections4:4.5.0")
    implementation("org.apache.commons:commons-math3:3.6")
    implementation("com.google.code.gson:gson:2.10")
    implementation("com.google.guava:guava:33.2.1-jre")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")

    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.jetbrains.kotlinx:lincheck:2.39")
}

kotlin {
    compilerOptions {
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

tasks.test {
    useJUnitPlatform()
}

kover {
    reports {
        filters {
            includes {
                packages("com.mckernant1.*")
            }
        }
    }
}

tasks["koverHtmlReport"].dependsOn(tasks.test)
tasks.build {
    dependsOn("koverHtmlReport")
}

publishing {
    repositories {
        maven {
            url = uri("s3://mvn.mckernant1.com/release")
            authentication {
                register("awsIm", AwsImAuthentication::class.java)
            }
        }
    }

    publications {
        create<MavenPublication>("default") {
            artifactId = "kotlin-utils"
            from(components["kotlin"])
            val sourcesJar by tasks.creating(Jar::class) {
                val sourceSets: SourceSetContainer by project
                from(sourceSets["main"].allSource)
                archiveClassifier.set("sources")
            }
            artifact(sourcesJar)
            pom {
                name.set("kotlin-utils")
                description.set("A light file system storage system")
                url.set("https://github.com/mckernant1/kotlin-utils")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("mckernant1")
                        name.set("Tom McKernan")
                        email.set("tmeaglei@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/mckernant1/kotlin-utils.git")
                    developerConnection.set("scm:git:ssh://github.com/mckernant1/kotlin-utils.git")
                    url.set("https://github.com/mckernant1/kotlin-utils")
                }
            }
        }
    }
}
