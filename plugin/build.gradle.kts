plugins {
    `java-gradle-plugin`
    `java-test-fixtures`
    groovy
    id("com.gradle.plugin-publish") version "1.3.0"
    `maven-publish`
    signing
    id("me.champeau.internal.build.functional-testing")
    `kotlin-dsl`
}

group = "com.vectorandvertex.gradle.includegit"

kotlin {
    jvmToolchain(19)
}

dependencies {
    implementation(libs.bundles.jgit)

    testFixturesApi(libs.groovy)
    testFixturesApi(libs.spock)
    testFixturesImplementation(gradleTestKit())
}

publishing {
    repositories {
        maven {
            name = "build"
            url  = uri("${buildDir}/repo")
        }
    }
}

signing {
    useGpgCmd()
    publishing.publications.configureEach {
        sign(this)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


gradlePlugin {
    val includeBuildPlugin by plugins.creating {
        id = "com.vectorandvertex.includegit"
        implementationClass = "me.champeau.gradle.igp.IncludeGitPlugin"
        tags = listOf("git", "included builds")
        description = "Adds support for including Git repositories"
        website = "https://melix.github.io/includegit-gradle-plugin/"
        vcsUrl = "https://github.com/0megaD/includegit-gradle-plugin"
    }

    plugins {
        named("includeBuildPlugin") {
            displayName = "Gradle Include Git repositories plugin"
        }
    }
}
