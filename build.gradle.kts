import java.util.Properties

plugins {
    id("org.springframework.boot") version "3.0.0-M3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.liquibase.gradle") version "2.1.1"
    java
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(18))
    }
}

configurations {
    compileOnly {
        extendsFrom(annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven(url = "https://repo.spring.io/milestone")
}

ext {
    set("testcontainersVersion", "1.17.2")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.liquibase:liquibase-core")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.assertj:assertj-core")

    liquibaseRuntime("org.liquibase:liquibase-core")
    liquibaseRuntime("org.liquibase:liquibase-groovy-dsl:3.0.2")
    liquibaseRuntime("info.picocli:picocli:4.6.3")
    liquibaseRuntime("org.postgresql:postgresql")
    liquibaseRuntime("org.yaml:snakeyaml")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${ext["testcontainersVersion"]}")
    }
}

tasks.test {
    useJUnitPlatform()
}

liquibase {
    val resourcesPath = "src/main/resources"
    val applicationPropertiesFile = file(resourcesPath).resolve("application.properties")
    val applicationProperties = Properties()
    applicationProperties.load(applicationPropertiesFile.reader())

    val activity = "main"
    activities.register(activity) {
        this.arguments = mapOf(
            "logLevel" to "info",
            "url" to applicationProperties["spring.datasource.url"],
            "username" to applicationProperties["spring.datasource.username"],
            "password" to applicationProperties["spring.datasource.password"],
            "changeLogFile" to applicationProperties["spring.liquibase.change-log"].toString()
                .replace("classpath:", "$resourcesPath/")
        )
        runList = activity
    }
}