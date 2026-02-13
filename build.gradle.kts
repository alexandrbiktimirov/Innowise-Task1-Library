plugins {
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitBom = "5.10.2"
val mapstruct = "1.5.5.Final"
val lombok = "1.18.32"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.mapstruct:mapstruct:1.6.3")
    implementation("org.postgresql:postgresql:42.7.7")
    implementation("org.aspectj:aspectjweaver:1.9.22.1")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("jakarta.annotation:jakarta.annotation-api:3.0.0")
    implementation("org.mapstruct:mapstruct:$mapstruct")

    compileOnly("org.projectlombok:lombok:$lombok")

    testCompileOnly("org.projectlombok:lombok:$lombok")
    testAnnotationProcessor("org.projectlombok:lombok:$lombok")

    testImplementation(platform("org.junit:junit-bom:$junitBom"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    annotationProcessor("org.projectlombok:lombok:$lombok")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstruct")
}

tasks.test {
    useJUnitPlatform()
}
