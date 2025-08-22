plugins {
    id("java")
    id("application")
    id("com.gradleup.shadow") version "9.0.1";
}

dependencies {
    implementation("net.minestom:minestom:2025.08.18-1.21.8")
    implementation(project((":common")))
    implementation("com.github.ben-manes.caffeine:caffeine:3.2.2")
}
application {
    mainClass.set("org.minigamzreborn.bytelyplay.hub.Main")
}