plugins {
    id("java")
    id("application")
    id("com.gradleup.shadow") version "9.0.1";
}

group = "org.minigamzreborn.bytelyplay.dirtbox"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.minestom:minestom:2025.08.18-1.21.8")
    implementation("ch.qos.logback:logback-classic:1.5.18")
    implementation("org.abstractvault.bytelyplay:AbstractVault:1.2.0-BETA")
    implementation("org.mongodb:mongodb-driver-sync:5.6.0")
    implementation(project((":common")))
}

tasks.test {
    useJUnitPlatform()
}
application {
    mainClass.set("org.minigamzreborn.bytelyplay.dirtbox.Main")
}