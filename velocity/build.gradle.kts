plugins {
    id("java")
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