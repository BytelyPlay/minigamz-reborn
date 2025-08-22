package org.minigamzreborn.bytelyplay.protocol.operationHandlers;

import lombok.Getter;
import lombok.Setter;

public abstract class ClientOperationsHandler {
    @Getter @Setter
    private static ClientOperationsHandler instance;
}
