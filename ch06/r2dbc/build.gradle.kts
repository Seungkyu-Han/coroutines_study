plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    war
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "seungkyu"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-mustache")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.security:spring-security-core")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // reactor tool
    implementation("io.projectreactor:reactor-tools")

    // r2dbc
    implementation("io.asyncer:r2dbc-mysql:0.9.2")

    // rxjava
    implementation("io.reactivex.rxjava3:rxjava")

    if (System.getProperty("os.arch") == "aarch64" && System.getProperty("os.name") == "Mac OS X") {
        runtimeOnly("io.netty:netty-resolver-dns-native-macos:_:osx-aarch_64")
    }

    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-rx3")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
