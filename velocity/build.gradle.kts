plugins {
    id("java")
    id("xyz.jpenilla.run-velocity") version "2.3.1"
}

group = "org.minigamzreborn.bytelyplay.velocity"

repositories {
    mavenCentral()
}

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    implementation(project(":common"))
}

tasks.test {
    useJUnitPlatform()
}
tasks.runVelocity {
    // Configure the Velocity version for our task.
    // This is the only required configuration besides applying the plugin.
    // Your plugin's jar (or shadowJar if present) will be used automatically.
    velocityVersion("3.4.0-SNAPSHOT")
    jvmArgs = listOf<String>("-Dvelocity.packet-decode-logging=true")
}