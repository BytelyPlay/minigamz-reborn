package org.minigamzreborn.bytelyplay.hub.impl;

import lombok.extern.slf4j.Slf4j;
import net.minestom.server.MinecraftServer;
import org.minigamzreborn.bytelyplay.protocol.operationHandlers.client.ClientOperationsHandler;

@Slf4j
public class ClientOperationsHandlerImpl extends ClientOperationsHandler {
    @Override
    public void serverDisconnected() {
        log.warn("Server disconnected, shutting down.");
        MinecraftServer.getServer().stop();
        System.exit(0);
    }
}
