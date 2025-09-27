plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-aop:5.3.39")
    implementation("org.aspectj:aspectjweaver:1.9.22.1")
    implementation("org.slf4j:slf4j-api:2.0.16")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.13")
    implementation("org.springframework:spring-context:7.0.0-M1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.20.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}