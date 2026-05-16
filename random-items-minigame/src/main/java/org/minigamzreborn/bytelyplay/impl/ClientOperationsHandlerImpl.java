package org.minigamzreborn.bytelyplay.impl;

import lombok.extern.slf4j.Slf4j;
import net.minecraft.server.MinecraftServer;
import org.minigamzreborn.bytelyplay.protocol.operationhandlers.client.ClientOperationsHandler;
import org.minigamzreborn.bytelyplay.Main;

@Slf4j
public class ClientOperationsHandlerImpl extends ClientOperationsHandler {
    @Override
    public void serverDisconnected() {
        MinecraftServer server = Main.getMinecraftServer();

        if (server == null) {
            log.error("Server disconnected and MinecraftServer == null, this won't work but won't shutdown forcefully so expect nothing to work.");
            return;
        }
        log.warn("Remote Server Disconnected, shutting down.");
        server.halt(false);
    }
}
