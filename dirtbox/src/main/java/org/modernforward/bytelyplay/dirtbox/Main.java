package org.modernforward.bytelyplay.dirtbox;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceManager;
import org.modernforward.bytelyplay.dirtbox.listeners.PlayerJoinHandlers;
import org.modernforward.bytelyplay.dirtbox.utils.Instances;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class Main {
    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init();

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();

        Instances.dirtbox = instanceManager.createInstanceContainer();

        setupEvents();

        // TODO: Make this configurable.
        server.start(new InetSocketAddress("0.0.0.0", 25569));
    }
    private static void setupEvents() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, PlayerJoinHandlers::asyncPlayerConfigEvent);
    }
}