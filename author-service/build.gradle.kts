plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.java)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.spring.boot.starter.implementation)

    implementation(libs.mapstruct)
    implementation(libs.postgresql)
    implementation(libs.aspectjweaver)
    implementation(libs.jakarta.annotation)
    implementation(libs.jjwt.api)

    runtimeOnly(libs.jjwt.impl)
    runtimeOnly(libs.jjwt.jackson)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.testcontainers.all)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.spring.security.test)
    testImplementation(libs.spring.boot.test)
    testImplementation(libs.spring.testcontainers)
    testRuntimeOnly(libs.junit.platform.launcher)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    annotationProcessor(libs.spring.boot.configuration.processor)
    annotationProcessor(libs.mapstruct.processor)
}

tasks.test {
    useJUnitPlatform()
}
