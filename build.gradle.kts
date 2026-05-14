plugins {
    id("java")
    id("com.gradleup.shadow") version "9.4.1";
    id("io.freefair.lombok") version "9.5.0"
}
allprojects {
    repositories {
        mavenCentral()

        maven { url = uri("https://jitpack.io") }
    }
}
subprojects {
    version = "1.0.0-BETA"

    apply(plugin = "java")
    apply(plugin = "com.gradleup.shadow")
    apply(plugin = "io.freefair.lombok")

    dependencies {
        compileOnly("org.jetbrains:annotations:26.0.2")
        compileOnly(
            "com.google.protobuf:protobuf-java:${project.property("protobuf_version") as String?}"
        )
    }

    tasks.shadowJar {
        minimize()
    }
    tasks.named("build") {
        dependsOn("shadowJar");
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_25
    }
}