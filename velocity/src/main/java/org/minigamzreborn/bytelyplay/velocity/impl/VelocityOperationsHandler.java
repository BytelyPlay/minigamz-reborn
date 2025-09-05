package org.minigamzreborn.bytelyplay.velocity.impl;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import lombok.extern.slf4j.Slf4j;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;
import org.minigamzreborn.bytelyplay.protocol.operationHandlers.Server.ServerOperationsHandler;
import org.minigamzreborn.bytelyplay.velocity.Main;
import org.minigamzreborn.bytelyplay.velocity.utils.Messages;
import org.minigamzreborn.bytelyplay.velocity.utils.ServerTypeRegistry;

import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class VelocityOperationsHandler extends ServerOperationsHandler {
    @Override
    public void addServer(String address, int port, ServerTypeOuterClass.ServerType type) {
        if (type == ServerTypeOuterClass.ServerType.RANDOM_ITEMS && ServerTypeRegistry.typeAndAddress.containsValue(type)) {
            log.warn("Not adding server because of a duplicated servertype of a servertype that doesn't support a second server of its type");
            return;
        }
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
            if (registeredServer.getServerInfo().getAddress().equals(socketAddress)) {
                ServerTypeRegistry.typeAndAddress.remove(registeredServer);
                server.unregisterServer(registeredServer.getServerInfo());
                removed[0] = true;
            }
        });
        if (!removed[0]) {
            log.warn("Unable to remove server {}:{} does it not exist?", socketAddress.getHostString(), socketAddress.getPort());
        }
    }

    @Override
    public void transferPlayer(UUID playerUUID, ServerTypeOuterClass.ServerType toServer) {
        ProxyServer server = Main.getInstance().getServer();
        Optional<RegisteredServer> optionalChosen = Main.getInstance().getRandomServerOfType(toServer);
        Optional<Player> optionalPlayer = server.getPlayer(playerUUID);

        if (optionalPlayer.isPresent()) {
            Player p = optionalPlayer.get();

            if (optionalChosen.isEmpty()) {
                log.warn("Tried to send a player but there are no servers available.");
                p.sendMessage(Messages.NO_SERVER_FOUND_TO_TRANSFER);
            }

            RegisteredServer chosen = optionalChosen.orElse(null);

            p.createConnectionRequest(chosen).fireAndForget();
        } else {
            log.warn("Tried to send a not-logged-in player to a server.");
        }
    }
}
