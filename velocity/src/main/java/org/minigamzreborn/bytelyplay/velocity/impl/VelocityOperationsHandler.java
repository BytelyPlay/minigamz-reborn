package org.minigamzreborn.bytelyplay.velocity.impl;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;
import org.minigamzreborn.bytelyplay.protocol.operationHandlers.Server.ServerOperationsHandler;
import org.minigamzreborn.bytelyplay.velocity.Main;
import org.minigamzreborn.bytelyplay.velocity.utils.ServerTypeRegistry;

import java.net.InetSocketAddress;
import java.util.UUID;

public class VelocityOperationsHandler extends ServerOperationsHandler {
    public VelocityOperationsHandler() {
        ServerOperationsHandler.setInstance(this);
    }
    @Override
    public void addServer(String address, int port, ServerTypeOuterClass.ServerType type) {
        ServerInfo info = new ServerInfo(UUID.randomUUID().toString(), new InetSocketAddress(address, port));
        ServerTypeRegistry.typeAndAddress.put(info, type);
        Main.getInstance().getServer().registerServer(info);
    }
}
