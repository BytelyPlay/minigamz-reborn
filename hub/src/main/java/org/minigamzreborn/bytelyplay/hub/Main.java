package org.minigamzreborn.bytelyplay.hub;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerSkinInitEvent;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.anvil.AnvilLoader;
import net.minestom.server.utils.mojang.MojangUtils;
import org.minigamzreborn.bytelyplay.hub.events.PlayerSkinEvent;

import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static Path HUB_WORLD_PATH = Paths.get("./hub_world");
    // Should run under proxy so no auth required.
    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init();

        setupEvents();
        setupInstances();

        server.start(new InetSocketAddress("0.0.0.0", 25565));
    }
    private static void setupInstances() {
        InstanceManager manager = MinecraftServer.getInstanceManager();
        manager.createInstanceContainer(new AnvilLoader(HUB_WORLD_PATH));
    }
    private static void setupEvents() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        globalEventHandler.addListener(PlayerSkinInitEvent.class, PlayerSkinEvent::skinInitEvent);
    }
}
