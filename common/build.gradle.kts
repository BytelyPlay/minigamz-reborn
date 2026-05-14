plugins {
    id("com.google.protobuf") version "0.10.0"
}
group = "org.minigamzreborn.bytelyplay.common"

dependencies {
    implementation("com.google.protobuf:protobuf-java:" +
            providers.gradleProperty("protobuf_version").get()
    )
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("io.netty:netty-all:4.2.13.Final")
}
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:" +
                providers.gradleProperty("protobuf_version").get()
    }
}
java {
    withSourcesJar()
}