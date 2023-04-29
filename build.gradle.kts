plugins {
    `maven-publish`
    `java-library`
    signing
    kotlin("jvm") version "1.8.21"
    id("org.jetbrains.kotlinx.kover") version "0.5.0"
}

group = "com.mckernant1.commons"
version = "0.2.1"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // Logging
    implementation("org.slf4j:slf4j-api:2.0.5")

    // Common
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.apache.commons:commons-collections4:4.4")
    implementation("org.apache.commons:commons-math3:3.6.1")
    implementation("com.google.code.gson:gson:2.10")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.1")

    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}

tasks.test {
    useJUnitPlatform()
    extensions.configure(kotlinx.kover.api.KoverTaskExtension::class) {
        isDisabled = false
        binaryReportFile.set(file("$buildDir/custom/result.bin"))
        includes = listOf("com.mckernant1.commons.*")
    }
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
