plugins {
    id("java")
    id("com.gradleup.shadow") version "9.0.1";
    id("io.freefair.lombok") version "8.14.2"
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
    apply(plugin = "io.freefair.lombok")
    tasks.named("build") {
        dependsOn("shadowJar");
    }
}