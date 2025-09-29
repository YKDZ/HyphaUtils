import java.util.Locale

plugins {
    `java-library`
    id("java")
    id("maven-publish")
    id("io.github.goooler.shadow") version "8.1.8"
}

group = "cn.encmys.ykdz.forest"
version = "0.1.0-Beta"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.extendedclip.com/releases/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.9-pre4-R0.1-SNAPSHOT")
    annotationProcessor("org.jetbrains:annotations:26.0.1")
    compileOnly("net.kyori:adventure-text-serializer-ansi:4.20.0")
    compileOnly("net.kyori:adventure-text-serializer-legacy:4.20.0")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    withJavadocJar()
    withSourcesJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(21)
}

tasks.shadowJar {
    archiveFileName.set(rootProject.name + "-" + project.version + ".jar")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ykdz/HyphaUtils")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GPR_USER")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GPR_KEY")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
            groupId = group as String
            artifactId = rootProject.name.lowercase(Locale.getDefault())
            version = version.lowercase(Locale.getDefault())
        }
    }
}