plugins {
    id("java")
    id("application")
    id("com.gradleup.shadow") version "9.4.1";
}

group = "org.minigamzreborn.bytelyplay.dirtbox"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("net.minestom:minestom:2026.04.13-1.21.11")
    implementation("ch.qos.logback:logback-classic:1.5.32")

    implementation("com.github.bytelyplay:abstract-vault:" +
            "${project.property("abstract_vault_version") as String?}") {
        isTransitive = true
    }
    implementation("org.mongodb:mongodb-driver-sync:5.7.0")
    implementation(project((":common")))
}
application {
    mainClass.set("org.minigamzreborn.bytelyplay.dirtbox.Main")
}