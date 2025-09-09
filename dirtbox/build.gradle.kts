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
    implementation(project((":common")))
}

tasks.test {
    useJUnitPlatform()
}
application {
    mainClass.set("org.minigamzreborn.bytelyplay.dirtbox.Main")
}