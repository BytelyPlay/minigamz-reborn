plugins {
    id("com.google.protobuf") version "0.9.5"
}
group = "org.minigamzreborn.bytelyplay.common"

dependencies {
    implementation("com.google.protobuf", "protobuf-java", "4.32.0")
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("io.netty:netty-all:4.2.4.Final")
}
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.32.0"
    }
}
java {
    withSourcesJar()
}