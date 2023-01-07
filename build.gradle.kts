import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.spring") version "1.8.0"
    kotlin("kapt") version "1.8.0"
}

group = "com.polarbookshop"

version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_17

repositories { mavenCentral() }

extra["springCloudVersion"] = "2022.0.0"
extra["testcontainersVersion"] = "1.17.6"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.retry:spring-retry")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.flywaydb:flyway-core")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.testcontainers:postgresql")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    runtimeOnly("org.postgresql:postgresql")
}

dependencyManagement {
    imports {
        mavenBom(
            "org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}"

        )
        mavenBom(
            "org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}"
        )
    }
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> { useJUnitPlatform() }

configurations { compileOnly { extendsFrom(annotationProcessor.get()) } }

tasks.register("bootRunTestData") {
    group = "application"
    description = "Runs the Spring Boot application with the testdata profile"
    doFirst { tasks.bootRun.configure { systemProperty("spring.profiles.active", "testdata") } }
    finalizedBy("bootRun")
}

tasks.named<BootBuildImage>("bootBuildImage") {
    imageName.set(project.name)
    environment.set(mapOf("BP_JVM_VERSION" to "17.*"))
    publish.set(false)
    docker {
        publishRegistry {
            username.set(project.findProperty("registryUsername")?.toString())
            password.set(project.findProperty("registryToken")?.toString())
            url.set(project.findProperty("registryUrl")?.toString())
        }
    }
}