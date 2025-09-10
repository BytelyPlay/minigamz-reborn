package org.minigamzreborn.bytelyplay.dirtbox;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.trait.InstanceEvent;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.anvil.AnvilLoader;
import org.minigamzreborn.bytelyplay.dirtbox.listeners.PlayerBlockBreakHandler;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.RegisterServerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protocol.ProtocolMain;
import org.minigamzreborn.bytelyplay.protocol.utils.Server;
import org.minigamzreborn.bytelyplay.dirtbox.listeners.PlayerJoinHandlers;
import org.minigamzreborn.bytelyplay.dirtbox.utils.Instances;

import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public Server protocolServer;
    public static void main(String[] args) {
        new Main();
    }
    public Main() {
        MinecraftServer server = MinecraftServer.init();

        setupInstances();

        setupEvents();
        setupProtocol();

        // TODO: Make this configurable.
        server.start(new InetSocketAddress("0.0.0.0", 25569));
    }
    private void setupEvents() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, PlayerJoinHandlers.getInstance()::asyncPlayerConfigEvent);

        EventNode<InstanceEvent> node = Instances.dirtbox.eventNode();
        node.addListener(PlayerBlockBreakEvent.class, PlayerBlockBreakHandler.getInstance()::blockBrokenDirtbox);
    }
    private void setupProtocol() {
        protocolServer = ProtocolMain.initClient("127.0.0.1", 9485);

        protocolServer.sendPacket(WrappedPacketC2SOuterClass.WrappedPacketC2S.newBuilder()
                .setRegisterServerPacket(RegisterServerPacketC2SOuterClass.RegisterServerPacketC2S.newBuilder()
                        .setAddress("127.0.0.1")
                        .setPort(25569)
                        .setType(ServerTypeOuterClass.ServerType.DIRTBOX)
                        .build())
                .build());
    }
    private void setupInstances() {
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();

        Instances.dirtbox = instanceManager.createInstanceContainer(new AnvilLoader(Path.of("./dirtbox_world/")));
    }
}