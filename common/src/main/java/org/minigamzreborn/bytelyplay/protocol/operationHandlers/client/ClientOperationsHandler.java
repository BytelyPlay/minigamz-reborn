package org.minigamzreborn.bytelyplay.protocol.operationHandlers.client;

import lombok.Getter;
import lombok.Setter;

public abstract class ClientOperationsHandler {
    @Getter @Setter
    private static ClientOperationsHandler instance;
    public abstract void serverDisconnected();
}
