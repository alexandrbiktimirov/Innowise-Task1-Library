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
val mapstruct = "1.5.5.Final"
val lombok = "1.18.32"

dependencies {
    implementation("org.springframework:spring-context:$spring")
    implementation("org.springframework:spring-orm:$spring")
    implementation("org.springframework:spring-aop:$spring")
    implementation("org.postgresql:postgresql:42.7.7")
    implementation("org.hibernate:hibernate-core:7.1.3.Final")
    implementation("org.hibernate.orm:hibernate-jcache:7.1.3.Final")
    implementation("com.github.ben-manes.caffeine:jcache:3.1.8")
    implementation("org.springframework:spring-tx:${spring}")
    implementation("org.aspectj:aspectjweaver:1.9.22.1")
    implementation("org.slf4j:slf4j-api:2.0.16")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.13")
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
    implementation("org.mapstruct:mapstruct:$mapstruct")
    compileOnly("org.projectlombok:lombok:$lombok")
    annotationProcessor("org.projectlombok:lombok:$lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstruct")
    testCompileOnly("org.projectlombok:lombok:$lombok")
    testAnnotationProcessor("org.projectlombok:lombok:$lombok")
    testImplementation(platform("org.junit:junit-bom:$junitBom"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
