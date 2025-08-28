package org.minigamzreborn.bytelyplay.velocity.impl;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import lombok.extern.slf4j.Slf4j;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;
import org.minigamzreborn.bytelyplay.protocol.operationHandlers.Server.ServerOperationsHandler;
import org.minigamzreborn.bytelyplay.velocity.Main;
import org.minigamzreborn.bytelyplay.velocity.utils.ServerTypeRegistry;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class VelocityOperationsHandler extends ServerOperationsHandler {
    @Override
    public void addServer(String address, int port, ServerTypeOuterClass.ServerType type) {
        ProxyServer server = Main.getInstance().getServer();
        ServerInfo info = new ServerInfo(UUID.randomUUID().toString(), new InetSocketAddress(address, port));
        RegisteredServer registeredServer = server.registerServer(info);
        ServerTypeRegistry.typeAndAddress.put(registeredServer, type);
    }

    @Override
    public void removeServer(String address, int port) {
        ProxyServer server = Main.getInstance().getServer();
        InetSocketAddress socketAddress = new InetSocketAddress(address, port);
        final boolean[] removed = {false};
        ServerTypeRegistry.typeAndAddress.forEach((registeredServer, serverType) -> {
            if (removed[0]) return;
            if (registeredServer.getServerInfo().getAddress() == socketAddress) {
                ServerTypeRegistry.typeAndAddress.remove(registeredServer);
                server.unregisterServer(registeredServer.getServerInfo());
                removed[0] = true;
            }
        });
    }

    @Override
    public void transferPlayer(UUID playerUUID, ServerTypeOuterClass.ServerType toServer) {
        ProxyServer server = Main.getInstance().getServer();
        List<RegisteredServer> options = new ArrayList<>();
        ServerTypeRegistry.typeAndAddress.forEach((regServer, type) -> {
            if (type == toServer) options.add(regServer);
        });
        if (options.isEmpty()) {
            log.warn("Tried to send a player but there are no servers available.");
            return;
        }

        RegisteredServer chosen = options.get(ThreadLocalRandom.current().nextInt(0, options.size()));
        Optional<Player> optionalPlayer = server.getPlayer(playerUUID);
        if (optionalPlayer.isPresent()) {
            Player p = optionalPlayer.get();
            p.createConnectionRequest(chosen);
        } else {
            log.warn("Tried to send a not-logged-in player to a server.");
        }
    }
}
