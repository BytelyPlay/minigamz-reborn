package org.minigamzreborn.bytelyplay.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.minigamzreborn.bytelyplay.protocol.ProtoServer;
import org.minigamzreborn.bytelyplay.protocol.ProtocolMain;
import org.minigamzreborn.bytelyplay.protocol.operationHandlers.Server.ServerOperationsHandler;
import org.minigamzreborn.bytelyplay.velocity.impl.VelocityOperationsHandler;
import org.slf4j.Logger;

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
        ProtocolMain.initServer(ip, port);
        logger.info("Listening on {}:{}", ip, port);
    }
}