plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val spring = "6.2.7"
val junitBom = "5.10.2"

dependencies {
    implementation("org.springframework:spring-context:$spring")
    implementation("org.springframework:spring-aop:$spring")
    implementation("org.aspectj:aspectjweaver:1.9.22.1")
    implementation("org.slf4j:slf4j-api:2.0.16")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.13")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.17.2")
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
    testImplementation(platform("org.junit:junit-bom:$junitBom"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}