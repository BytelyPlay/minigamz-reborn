package org.minigamzreborn.bytelyplay.dirtbox;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import lombok.Getter;
import net.minestom.server.Auth;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.Command;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.*;
import net.minestom.server.event.trait.InstanceEvent;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.timer.SchedulerManager;
import org.bson.UuidRepresentation;
import org.jetbrains.annotations.NotNull;
import org.minigamzreborn.bytelyplay.dirtbox.commands.BuyShovelsCommand;
import org.minigamzreborn.bytelyplay.dirtbox.constants.ChunkLoaders;
import org.minigamzreborn.bytelyplay.dirtbox.constants.Instances;
import org.minigamzreborn.bytelyplay.dirtbox.constants.MongoDBConstants;
import org.minigamzreborn.bytelyplay.dirtbox.listeners.*;
import org.minigamzreborn.bytelyplay.dirtbox.utils.Config;
import org.minigamzreborn.bytelyplay.dirtbox.utils.MapRegenerationHelpers;
import org.minigamzreborn.bytelyplay.dirtbox.utils.SaveLoadPlayerData;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.RegisterServerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protocol.ProtocolMain;
import org.minigamzreborn.bytelyplay.protocol.utils.Server;

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
        loadConfig();

        MinecraftServer server = MinecraftServer.init(new Auth.Velocity(Config.getInstance().getForwardingSecret()));

        setupMongoDB();

        setupInstances();
        setupEvents();

        setupProtocol();
        setupScheduledTasks();
        setupCommands();

        // TODO: Make this configurable.
        server.start(new InetSocketAddress("0.0.0.0", 25569));
    }
    private void setupEvents() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, PlayerJoinHandlers.getInstance()::asyncPlayerConfigEvent);

        EventNode<@NotNull InstanceEvent> dirtboxNode = Instances.dirtbox.eventNode();
        dirtboxNode.addListener(PlayerBlockBreakEvent.class, PlayerBlockBreakHandler.getInstance()::blockBrokenDirtbox);
        dirtboxNode.addListener(PlayerMoveEvent.class, NoVoidFalling::playerMove);
        dirtboxNode.addListener(PlayerBlockPlaceEvent.class, PlayerBlockPlaceHandler::blockPlace);
        dirtboxNode.addListener(PlayerDisconnectEvent.class, SavePlayerDataListeners::saveData);
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
        manager.buildShutdownTask(SaveLoadPlayerData::saveAllPlayerInventories);
        manager.buildShutdownTask(MongoDBConstants.client::close);

        manager.buildTask(MapRegenerationHelpers::regenerateDirtboxMap)
                .repeat(Duration.ofMinutes(5))
                .schedule();
        manager.buildTask(SaveLoadPlayerData::saveAllPlayerInventories)
                .repeat(Duration.ofSeconds(30))
                .schedule();
    }
    private void loadConfig() {
        Config.getInstance().loadConfig();
    }

    private void setupMongoDB() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(
                        new ConnectionString(
                                Config.getInstance().getMongoDBConnectionString()
                        )
                )
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build();
        MongoDBConstants.client = MongoClients.create(
                settings
        );
        MongoDBConstants.database = MongoDBConstants.client.getDatabase("minigamz_reborn");
        MongoDBConstants.playerInventoryCollection = MongoDBConstants.database.getCollection("player_inventories");
    }
    private void setupCommands() {
        CommandManager manager = MinecraftServer.getCommandManager();
        manager.register(new BuyShovelsCommand());
    }
}