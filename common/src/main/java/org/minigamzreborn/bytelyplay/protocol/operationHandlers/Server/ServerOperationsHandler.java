package org.minigamzreborn.bytelyplay.protocol.operationHandlers.Server;

import lombok.Getter;
import lombok.Setter;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;

public abstract class ServerOperationsHandler {
    @Getter @Setter
    private static ServerOperationsHandler instance;
    public abstract void addServer(String address, int port, ServerTypeOuterClass.ServerType type);
}
