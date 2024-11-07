plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

kotlin {
    jvmToolchain(19)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(19)
    }
}

dependencies {
    implementation("org.asciidoctor:asciidoctor-gradle-jvm:3.3.2")
    implementation("org.ajoberstar:gradle-git-publish:3.0.0")
}
