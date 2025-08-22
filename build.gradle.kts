plugins {
    id("java")
    id("com.gradleup.shadow") version "9.0.1";
}

group = "org.minigamzreborn.bytelyplay"
version = "1.0-INDEV"

allprojects {
    repositories {
        mavenCentral()
    }
}
subprojects {
    apply(plugin = "java")
    apply(plugin = "com.gradleup.shadow")
    tasks.named("build") {
        dependsOn("shadowJar");
    }
}