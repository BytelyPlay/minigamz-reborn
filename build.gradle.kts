plugins {
    id("java")
    id("com.gradleup.shadow") version "9.0.1";
}
allprojects {
    repositories {
        mavenCentral()
    }
}
subprojects {
    version = "1.0-INDEV"
    apply(plugin = "java")
    apply(plugin = "com.gradleup.shadow")
    tasks.named("build") {
        dependsOn("shadowJar");
    }
}