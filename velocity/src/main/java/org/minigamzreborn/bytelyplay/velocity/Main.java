package org.minigamzreborn.bytelyplay.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.minigamzreborn.bytelyplay.protocol.ProtoServer;
import org.minigamzreborn.bytelyplay.protocol.ProtocolMain;
import org.slf4j.Logger;

@Plugin(
        id = "minigamzreborn-velocity",
        name = "MinigamzReborn velocity",
        version = "TODO",
        description = "The velocity plugin to connect all the servers"
)
public class Main {
    @Getter()
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
    }
    @Subscribe
    public void onProxyInitialized(ProxyInitializeEvent event) {
        ProtocolMain.initServer();
    }
}