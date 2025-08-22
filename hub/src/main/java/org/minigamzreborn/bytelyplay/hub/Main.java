package org.minigamzreborn.bytelyplay.hub;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSkinInitEvent;
import net.minestom.server.event.trait.InstanceEvent;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.anvil.AnvilLoader;
import net.minestom.server.utils.mojang.MojangUtils;
import org.minigamzreborn.bytelyplay.hub.events.PlayerLoginHandler;
import org.minigamzreborn.bytelyplay.hub.events.PlayerSkinEvent;
import org.minigamzreborn.bytelyplay.hub.utils.Config;
import org.minigamzreborn.bytelyplay.hub.utils.Constants;
import org.minigamzreborn.bytelyplay.hub.utils.Instances;
import org.minigamzreborn.bytelyplay.protobuffer.packets.HandShakePacketS2COuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.PacketWrapperOuterClass;
import org.minigamzreborn.bytelyplay.protocol.Constants.SharedConstants;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.minigamzreborn.bytelyplay.hub.utils.Constants.HUB_CONFIG_PATH;
import static org.minigamzreborn.bytelyplay.hub.utils.Constants.HUB_WORLD_PATH;

public class Main {
    // Should run under proxy so no auth required.
    public Main() {
        MinecraftServer server = MinecraftServer.init();

        parseJson();
        setupEvents();
        setupInstances();

        server.start(new InetSocketAddress("0.0.0.0", 25565));
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
        // EventNode<InstanceEvent> hubEventNode = Instances.hub.eventNode();
    }
    public void parseJson() {
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
}
