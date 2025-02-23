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
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    annotationProcessor("org.jetbrains:annotations:26.0.1")
    implementation("net.kyori:adventure-text-serializer-ansi:4.18.0")
    implementation("net.kyori:adventure-text-serializer-legacy:4.18.0")
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

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("*plugin.yml") {
        expand(props)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "cn.encmys"
            artifactId = rootProject.name
            version = rootProject.version.toString()

            pom {
                name.set(rootProject.name)
                description.set("Common utils for Hypha plugins.")
                url.set("https://github.com/YKDZ")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("ykdz")
                        name.set("YKDZ")
                        email.set("307079958@qq.com")
                    }
                }
            }
        }
    }

    repositories {
        mavenLocal()
    }
}