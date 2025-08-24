rootProject.name = "MinigamzReborn"

pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
    }
}


include("common")
include("hub")
include("random-items-minigame")
include("velocity")