package org.minigamzreborn.bytelyplay.protocol.operationHandlers;

import lombok.Getter;
import lombok.Setter;

public abstract class ServerOperationsHandler {
    @Getter @Setter
    private static ServerOperationsHandler instance;
}
