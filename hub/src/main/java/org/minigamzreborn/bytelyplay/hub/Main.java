package org.minigamzreborn.bytelyplay.hub;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.minestom.server.Auth;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import net.minestom.server.event.player.PlayerSkinInitEvent;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.anvil.AnvilLoader;
import net.minestom.server.timer.SchedulerManager;
import org.minigamzreborn.bytelyplay.hub.NPCs.DirtBoxNPC;
import org.minigamzreborn.bytelyplay.hub.NPCs.RandomItemsNPC;
import org.minigamzreborn.bytelyplay.hub.events.NPCInteractionEvents;
import org.minigamzreborn.bytelyplay.hub.events.PlayerLoginHandler;
import org.minigamzreborn.bytelyplay.hub.events.PlayerSkinEvent;
import org.minigamzreborn.bytelyplay.hub.impl.ClientOperationsHandlerImpl;
import org.minigamzreborn.bytelyplay.hub.scheduled.ShutdownHandler;
import org.minigamzreborn.bytelyplay.hub.utils.Config;
import org.minigamzreborn.bytelyplay.hub.utils.Constants;
import org.minigamzreborn.bytelyplay.hub.utils.Instances;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.RegisterServerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protocol.Constants.SharedConstants;
import org.minigamzreborn.bytelyplay.protocol.ProtocolMain;
import org.minigamzreborn.bytelyplay.protocol.operationHandlers.client.ClientOperationsHandler;
import org.minigamzreborn.bytelyplay.protocol.utils.Server;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;

import static org.minigamzreborn.bytelyplay.hub.utils.Constants.HUB_CONFIG_PATH;
import static org.minigamzreborn.bytelyplay.hub.utils.Constants.HUB_WORLD_PATH;

@Slf4j
public class Main {
    @Getter
    private final String ipToRegisterWith;
    @Getter
    private static Main instance;
    @Getter
    private final Server server;
    // Should run under proxy so no auth required.
    public Main() {
        parseConfig();

        MinecraftServer server = MinecraftServer.init(new Auth.Velocity(Config.getInstance().getSecret()));
        ipToRegisterWith = "127.0.0.1";
        int port = Config.getInstance().getPort();

        instance = this;

        setupEvents();
        setupInstances();
        this.server = setupProtocol();

        setupServer();
        setupScheduledTasks();

        server.start(new InetSocketAddress("0.0.0.0", port));
        setupNPCs();
    }
    public static void main(String[] args) {
        new Main();
    }
    private void setupInstances() {
        InstanceManager manager = MinecraftServer.getInstanceManager();
        Instances.hub = manager.createInstanceContainer(new AnvilLoader(HUB_WORLD_PATH));
    }
    private void setupEvents() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        globalEventHandler.addListener(PlayerSkinInitEvent.class, PlayerSkinEvent::skinInitEvent);
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, PlayerLoginHandler::asyncPlayerConfigEvent);
        globalEventHandler.addListener(PlayerEntityInteractEvent.class, NPCInteractionEvents::playerEntityInteractEvent);
        globalEventHandler.addListener(EntityAttackEvent.class, NPCInteractionEvents::entityAttackEvent);
        // EventNode<InstanceEvent> hubEventNode = Instances.hub.eventNode();
    }
    private void parseConfig() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (Files.exists(Constants.HUB_CONFIG_PATH)) {
                Config.deserialize(mapper.readTree(new FileInputStream(HUB_CONFIG_PATH.toString())));
            } else {
                Files.createDirectories(SharedConstants.CONFIGURATION_FOLDER);
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(HUB_CONFIG_PATH.toString()));
                mapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, Config.serialize());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Server setupProtocol() {
        ClientOperationsHandler.setInstance(new ClientOperationsHandlerImpl());
        return ProtocolMain.initClient("127.0.0.1", 9485);
    }
    private void setupServer() {
        server.sendPacket(
                WrappedPacketC2SOuterClass.WrappedPacketC2S
                .newBuilder()
                .setRegisterServerPacket(
                        RegisterServerPacketC2SOuterClass.RegisterServerPacketC2S
                                .newBuilder()
                                .setAddress(ipToRegisterWith)
                                .setPort(Config.getInstance().getPort())
                                .build()
                )
                .build()
        );
    }
    private void setupNPCs() {
        RandomItemsNPC randomItemsNPC = new RandomItemsNPC();
        DirtBoxNPC dirtBoxNPC = new DirtBoxNPC();

        randomItemsNPC.setInstance(Instances.hub, new Pos(0.5, 11, 2.5, -180, 0));
        // TODO: place it on a diamond block.
        dirtBoxNPC.setInstance(Instances.hub, new Pos(-1.5, 11, 0.5, 90, 0));
    }
    private void setupScheduledTasks() {
        SchedulerManager manager = MinecraftServer.getSchedulerManager();
        manager.buildShutdownTask(ShutdownHandler::shutdown);
    }
}
