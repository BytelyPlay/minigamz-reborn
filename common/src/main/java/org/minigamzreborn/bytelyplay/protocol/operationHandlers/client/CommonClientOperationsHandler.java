package org.minigamzreborn.bytelyplay.protocol.operationHandlers.client;

import lombok.extern.slf4j.Slf4j;
import org.minigamzreborn.bytelyplay.protobuffer.packets.s2c.DisconnectPacketS2COuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.s2c.HandShakePacketS2COuterClass;
import org.minigamzreborn.bytelyplay.protocol.Constants.SharedConstants;
import org.minigamzreborn.bytelyplay.protocol.utils.Server;

@Slf4j
public class CommonClientOperationsHandler {
    public static void handShaked(HandShakePacketS2COuterClass.HandShakePacketS2C packet, Server server) {
        if (packet.getId() == SharedConstants.HAND_SHAKE_ID_VALUE) {
            server.setHandShaked(true);
            server.flush();
        }
    }

    public static void disconnect(DisconnectPacketS2COuterClass.DisconnectPacketS2C disconnect, Server server) {
        server.setDisconnected(true);
        server.disconnect();

        ClientOperationsHandler.getInstance().serverDisconnected();
    }
}
