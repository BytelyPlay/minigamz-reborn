package org.modernforward.bytelyplay.dirtbox;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceManager;
import org.minigamzreborn.bytelyplay.protobuffer.packets.RegisterServerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protocol.ProtocolMain;
import org.minigamzreborn.bytelyplay.protocol.utils.Server;
import org.modernforward.bytelyplay.dirtbox.listeners.PlayerJoinHandlers;
import org.modernforward.bytelyplay.dirtbox.utils.Instances;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class Main {
    public Server protocolServer;
    public static void main(String[] args) {
        new Main();
    }
    public Main() {
        MinecraftServer server = MinecraftServer.init();

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();

        Instances.dirtbox = instanceManager.createInstanceContainer();

        setupEvents();
        setupProtocol();

        // TODO: Make this configurable.
        server.start(new InetSocketAddress("0.0.0.0", 25569));
    }
    private void setupEvents() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, PlayerJoinHandlers::asyncPlayerConfigEvent);
    }
    private void setupProtocol() {
        protocolServer = ProtocolMain.initClient("127.0.0.1", 9485);

        protocolServer.sendPacket(WrappedPacketC2SOuterClass.WrappedPacketC2S.newBuilder()
                .setRegisterServerPacket(RegisterServerPacketC2SOuterClass.RegisterServerPacketC2S.newBuilder()
                        .setAddress("127.0.0.1")
                        .setPort(25569)
                        .build())
                .build());
    }
}