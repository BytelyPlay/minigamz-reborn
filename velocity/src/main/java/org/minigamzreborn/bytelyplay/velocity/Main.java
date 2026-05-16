package org.minigamzreborn.bytelyplay.velocity;

import com.google.inject.Inject;
import com.mojang.brigadier.CommandDispatcher;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.bytelyplay.brigadierHelpers.utils.Commands;
import org.minigamzreborn.bytelyplay.protocol.ProtoServer;
import org.minigamzreborn.bytelyplay.velocity.commands.HubCommand;
import org.minigamzreborn.bytelyplay.velocity.listeners.HandleAllCommands;
import org.minigamzreborn.bytelyplay.velocity.listeners.PlayerJoinListener;
import lombok.Getter;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;
import org.minigamzreborn.bytelyplay.protocol.ProtocolMain;
import org.minigamzreborn.bytelyplay.protocol.operationhandlers.server.ServerOperationsHandler;
import org.minigamzreborn.bytelyplay.velocity.impl.VelocityOperationsHandler;
import org.minigamzreborn.bytelyplay.velocity.utils.Config;
import org.minigamzreborn.bytelyplay.velocity.utils.ServerTypeRegistry;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    private Commands<CommandSource> commands;
    @Getter
    private final Logger logger;
    @Getter
    private final ProxyServer server;
    @Getter
    private ProtoServer protocolServer;

    @Getter
    private static Main instance;

    @Inject
    public Main(ProxyServer server, Logger logger,
                @DataDirectory Path dataDirectory) {
        instance = this;
        this.logger = logger;
        this.server = server;

        setupConfig(dataDirectory);
        ServerOperationsHandler.setInstance(new VelocityOperationsHandler());
    }
    @Subscribe
    public void onProxyInitialized(ProxyInitializeEvent event) {
        commands = new Commands<>(new CommandDispatcher<>());

        String ip = Config.getInstance().getIp();
        int port = Config.getInstance().getProtocolListenPort();

        protocolServer = ProtocolMain.initServer(ip, port);

        logger.info("Listening on {}:{}", ip, port);

        server.getEventManager().register(this, new PlayerJoinListener());
        server.getEventManager().register(this, new HandleAllCommands());

        commands.register(new HubCommand());
    }
    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent e) {
        logger.info("Shutting down Server.");
        protocolServer.shutdown();
    }
    private void setupConfig(Path dataDirectory) {
        try {
            Files.createDirectories(dataDirectory);

            Config.setConfigFile(dataDirectory);
            Config.getInstance().init();
        } catch (IOException e) {
            logger.error("Couldn't init config.", e);
            server.shutdown();
        }
    }
}