package org.minigamzreborn.bytelyplay.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.minigamzreborn.bytelyplay.protocol.ProtoServer;
import org.minigamzreborn.bytelyplay.protocol.utils.Server;
import org.minigamzreborn.bytelyplay.velocity.listeners.HandleAllCommands;
import org.minigamzreborn.bytelyplay.velocity.listeners.PlayerJoinListener;
import lombok.Getter;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;
import org.minigamzreborn.bytelyplay.protocol.ProtocolMain;
import org.minigamzreborn.bytelyplay.protocol.operationHandlers.server.ServerOperationsHandler;
import org.minigamzreborn.bytelyplay.velocity.impl.VelocityOperationsHandler;
import org.minigamzreborn.bytelyplay.velocity.utils.ServerTypeRegistry;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Plugin(
        id = "minigamzreborn-velocity",
        name = "MinigamzReborn velocity",
        // figure out a way to use this from something in the gradle.properties
        version = "1.0-INDEV",
        description = "The velocity plugin to connect all the servers"
)
public class Main {
    @Getter
    private final Logger logger;
    @Getter
    private final ProxyServer server;
    @Getter
    private ProtoServer protocolServer;

    @Getter
    private static Main instance;

    @Inject
    public Main(ProxyServer server, Logger logger) {
        instance = this;
        this.logger = logger;
        this.server = server;

        ServerOperationsHandler.setInstance(new VelocityOperationsHandler());
    }
    @Subscribe
    public void onProxyInitialized(ProxyInitializeEvent event) {
        String ip = "0.0.0.0";
        int port = 9485;
        protocolServer = ProtocolMain.initServer(ip, port);
        logger.info("Listening on {}:{}", ip, port);

        server.getEventManager().register(this, new PlayerJoinListener());
        server.getEventManager().register(this, new HandleAllCommands());
    }
    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent e) {
        logger.info("Shutting down Server.");
        protocolServer.shutdown();
    }
    public Optional<RegisteredServer> getRandomServerOfType(ServerTypeOuterClass.ServerType toType) {
        List<RegisteredServer> options = new ArrayList<>();
        ServerTypeRegistry.typeAndAddress.forEach((regServer, type) -> {
            if (type == toType) options.add(regServer);
        });
        if (options.isEmpty()) return Optional.empty();

        return Optional.of(
                options.get(ThreadLocalRandom.current().nextInt(0, options.size()))
        );
    }
}