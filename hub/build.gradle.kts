plugins {
    id("java")
    id("application")
    id("com.gradleup.shadow") version "9.0.1";
}

dependencies {
    implementation("net.minestom:minestom:2025.08.18-1.21.8")
    implementation(project((":common")))
    implementation("com.github.ben-manes.caffeine:caffeine:3.2.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0-rc1")
    implementation("ch.qos.logback:logback-classic:1.5.18")
    // from maven local, check out abstract-vault github repository.
    implementation("org.abstractvault.bytelyplay:AbstractVault:1.1.1-BETA")
}
application {
    mainClass.set("org.minigamzreborn.bytelyplay.hub.Main")
}
group = "org.minigamzreborn.bytelyplay.hub"