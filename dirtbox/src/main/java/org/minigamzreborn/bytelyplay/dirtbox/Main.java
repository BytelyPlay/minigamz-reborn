package org.minigamzreborn.bytelyplay.dirtbox;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import lombok.Getter;
import net.minestom.server.Auth;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.*;
import net.minestom.server.event.trait.InstanceEvent;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.timer.SchedulerManager;
import org.minigamzreborn.bytelyplay.dirtbox.constants.MongoDBConstants;
import org.minigamzreborn.bytelyplay.dirtbox.listeners.*;
import org.minigamzreborn.bytelyplay.dirtbox.constants.ChunkLoaders;
import org.minigamzreborn.bytelyplay.dirtbox.constants.Config;
import org.minigamzreborn.bytelyplay.dirtbox.utils.MapRegenerationHelpers;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.RegisterServerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protocol.ProtocolMain;
import org.minigamzreborn.bytelyplay.protocol.utils.Server;
import org.minigamzreborn.bytelyplay.dirtbox.constants.Instances;

import java.net.InetSocketAddress;
import java.time.Duration;

public class Main {
    @Getter
    private static Main instance;
    @Getter
    private Server protocolServer;

    public static void main(String[] args) {
        new Main();
    }
    public Main() {
        instance = this;

        MinecraftServer server = MinecraftServer.init(new Auth.Velocity(Config.getInstance().getForwardingSecret()));

        loadConfig();

        setupMongoDB();

        setupInstances();

        setupEvents();
        setupProtocol();
        setupScheduledTasks();

        // TODO: Make this configurable.
        server.start(new InetSocketAddress("0.0.0.0", 25569));
    }
    private void setupEvents() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, PlayerJoinHandlers.getInstance()::asyncPlayerConfigEvent);

        EventNode<InstanceEvent> dirtboxNode = Instances.dirtbox.eventNode();
        dirtboxNode.addListener(PlayerBlockBreakEvent.class, PlayerBlockBreakHandler.getInstance()::blockBrokenDirtbox);
        dirtboxNode.addListener(PlayerMoveEvent.class, NoVoidFalling::playerMove);
        dirtboxNode.addListener(PlayerBlockPlaceEvent.class, PlayerBlockPlaceHandler::blockPlace);
        dirtboxNode.addListener(PlayerDisconnectEvent.class, SaveLoadPlayerData::saveData);
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

        Instances.dirtbox = instanceManager.createInstanceContainer(ChunkLoaders.dirtboxChunkLoader);
    }
    private void setupScheduledTasks() {
        SchedulerManager manager = MinecraftServer.getSchedulerManager();

        manager.buildShutdownTask(protocolServer::disconnect);
        manager.buildShutdownTask(MongoDBConstants.client::close);

        manager.buildTask(MapRegenerationHelpers::regenerateDirtboxMap)
                .repeat(Duration.ofMinutes(5))
                .schedule();
    }
    private void loadConfig() {
        Config.getInstance().loadConfig();
    }

    private void setupMongoDB() {
        MongoDBConstants.client = MongoClients.create(
                new ConnectionString(
                        Config.getInstance().getMongoDBConnectionString()
                )
        );
        MongoDBConstants.database = MongoDBConstants.client.getDatabase("MinigamzReborn");
        MongoDBConstants.playerInventoryCollection = MongoDBConstants.database.getCollection("playerInventories");
    }
}