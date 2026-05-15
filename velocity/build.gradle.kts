plugins {
    id("java")
    id("xyz.jpenilla.run-velocity") version "3.0.2"
}

group = "org.minigamzreborn.bytelyplay.velocity"

repositories {
    mavenCentral()
    mavenLocal()
}

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.5.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.5.0-SNAPSHOT")

    implementation(project(":common"))

    implementation("com.github.bytelyplay:brigadier-helper:" +
            providers.gradleProperty("brigadier_helpers_version").get()) {
        isTransitive = false
    }
    implementation("tools.jackson.core:jackson-databind:3.1.3")
}

tasks.runVelocity {
    // Configure the Velocity version for our task.
    // This is the only required configuration besides applying the plugin.
    // Your plugin's jar (or shadowJar if present) will be used automatically.
    velocityVersion("3.5.0-SNAPSHOT")
    jvmArgs = listOf<String>("-Dvelocity.packet-decode-logging=true")
}