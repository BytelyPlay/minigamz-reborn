package org.minigamzreborn.bytelyplay.protocol.operationHandlers.client;

import org.minigamzreborn.bytelyplay.protobuffer.packets.s2c.HandShakePacketS2COuterClass;
import org.minigamzreborn.bytelyplay.protocol.Constants.SharedConstants;
import org.minigamzreborn.bytelyplay.protocol.utils.Server;

public class CommonClientOperationsHandler {
    public static void handShaked(HandShakePacketS2COuterClass.HandShakePacketS2C packet, Server server) {
        if (packet.getId() == SharedConstants.HAND_SHAKE_ID_VALUE) {
            server.setHandShaked(true);
            server.flush();
        }
    }
}
