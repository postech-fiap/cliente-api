import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.jetbrains.kotlinx.kover") version "0.5.0"
    id("org.sonarqube") version "4.4.1.3373"
    id("org.springframework.boot") version "3.1.0" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.spring") version "1.9.21" apply false
}

java.sourceCompatibility = JavaVersion.VERSION_17


allprojects {
    group = "br.com.fiap"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "kotlin")
    apply(plugin = "project-report")
    apply(plugin = "org.sonarqube")

    repositories {
        mavenCentral()
    }
}


tasks.withType<BootJar> {
    group = "build"
    dependsOn(":app:api:bootJar")
    doLast {
        ant.withGroovyBuilder {
            val jarPath = "${rootProject.buildDir}/../app/api/build/libs/app.jar"
            val jarDestination = "${rootProject.buildDir}/libs"
            "move"("file" to jarPath, "todir" to jarDestination)
        }
    }
}

tasks.withType<BootRun> {
    group = "application"
    dependsOn(":app:api:bootRun")
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin")
    apply(plugin = "org.sonarqube")

    sonar {
        properties {
            property("sonar.projectKey", "postech-fiap_cliente-api")
            property("sonar.organization", "postech-fiap")
            property("sonar.host.url", "https://sonarcloud.io")
        }
    }


    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.cucumber:cucumber-java8:7.15.0")
        testImplementation("io.cucumber:cucumber-junit:7.15.0")
        testImplementation("org.junit.platform:junit-platform-suite-api:1.10.1")
        testImplementation("io.rest-assured:rest-assured:5.4.0")
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "17"
            }
        }

        withType<Test> {
            useJUnitPlatform()
            testLogging {
                events("PASSED", "SKIPPED", "FAILED")
            }
        }

        withType<Copy> {
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
        }
    }
}

tasks.withType<BootJar> {
    group = "build"
    dependsOn(":app:api:bootJar")
    doLast {
        ant.withGroovyBuilder {
            val jarPath = "${rootProject.buildDir}/../app/api/build/libs/app.jar"
            val jarDestination = "${rootProject.buildDir}/libs"
            "move"("file" to jarPath, "todir" to jarDestination)
        }
    }
}

tasks.withType<BootRun> {
    group = "application"
    dependsOn(":app:api:bootRun")
}

sonar {
    properties {
        property("sonar.projectKey", "postech-fiap_cliente-api")
        property("sonar.organization", "postech-fiap")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

kover {
    isDisabled = false
    coverageEngine.set(kotlinx.kover.api.CoverageEngine.INTELLIJ)
    intellijEngineVersion.set("1.0.656")
    jacocoEngineVersion.set("0.8.8")
    generateReportOnCheck = true
    disabledProjects = setOf()
    instrumentAndroidPackage = false
    runAllTestsForProjectTask = false
}

val includeCoverage = listOf(
    "br.com.fiap.cliente.*",
)

val excludeCoverage = listOf(
    "**/config/*",
    "**/models/*",
    "**/dtos/*",
    "**/enums/*",
    "**/entities/*",
    "**/exceptions/*",
    "**/requests/*",
    "**/responses/*"
)

tasks.test {
    extensions.configure(kotlinx.kover.api.KoverTaskExtension::class) {
        isDisabled = false
        binaryReportFile.set(file("${layout.buildDirectory}/custom/result.bin"))
        includes = includeCoverage
        excludes = excludeCoverage
    }
}

tasks.koverMergedHtmlReport {
    isEnabled = true
    htmlReportDir.set(layout.buildDirectory.dir("reports/jacoco/test/html"))
    includes = includeCoverage
    excludes = excludeCoverage
}

tasks.koverMergedXmlReport {
    isEnabled = true
    xmlReportFile.set(layout.buildDirectory.file("reports/jacoco/test/jacocoTestReport.xml"))
    includes = includeCoverage
    excludes = excludeCoverage
}

tasks.register("jacocoTestReport") {
    dependsOn("test", "koverMergedHtmlReport", "koverMergedXmlReport")
}
