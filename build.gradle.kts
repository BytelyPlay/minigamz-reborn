plugins {
    id("java")
    id("com.gradleup.shadow") version "9.0.1";
    id("io.freefair.lombok") version "8.14.2"
}
allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}
subprojects {
    version = "1.0.1-ALPHA"

    apply(plugin = "java")
    apply(plugin = "com.gradleup.shadow")
    apply(plugin = "io.freefair.lombok")

    dependencies {
        compileOnly("org.jetbrains:annotations:26.0.2")
        compileOnly("com.google.protobuf", "protobuf-java", project.property("PROTOBUF_VERSION") as String?)
    }

    tasks.shadowJar {
        minimize()
    }
    tasks.named("build") {
        dependsOn("shadowJar");
    }
}