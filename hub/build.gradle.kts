plugins {
    id("java")
    id("application")
    id("com.gradleup.shadow") version "9.0.1";
}

dependencies {
    implementation("net.minestom:minestom:2026.04.13-1.21.11")
    implementation(project((":common")))
    implementation("com.github.ben-manes.caffeine:caffeine:3.2.2")
    implementation("tools.jackson.core:jackson-databind:3.1.3")
    implementation("ch.qos.logback:logback-classic:1.5.32")
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("com.github.bytelyplay:abstract-vault:${project.property("abstract_vault_version") as String?}")
}
application {
    mainClass.set("org.minigamzreborn.bytelyplay.hub.Main")
}
group = "org.minigamzreborn.bytelyplay.hub"