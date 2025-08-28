package org.minigamzreborn.bytelyplay.protocol.operationHandlers.Server;

import lombok.Getter;
import lombok.Setter;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;

import java.util.UUID;

public abstract class ServerOperationsHandler {
    @Getter @Setter
    private static ServerOperationsHandler instance;
    public abstract void addServer(String address, int port, ServerTypeOuterClass.ServerType type);
    public abstract void removeServer(String address, int port);
    public abstract void transferPlayer(UUID playerUUID, ServerTypeOuterClass.ServerType toServer);
}
