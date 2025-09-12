package org.minigamzreborn.bytelyplay.protocol.operationHandlers.server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.minigamzreborn.bytelyplay.protobuffer.packets.*;
import org.minigamzreborn.bytelyplay.protocol.Constants.SharedConstants;
import org.minigamzreborn.bytelyplay.protocol.utils.Client;

import java.util.UUID;

@Slf4j
public class CommonServerOperationsHandler {
    @Getter
    private static CommonServerOperationsHandler instance;

    public static void handShake(HandShakePacketC2SOuterClass.HandShakePacketC2S packet, Client client) {
        if (packet.getId() == SharedConstants.HAND_SHAKE_ID_VALUE) {
            client.sendPacket(WrappedPacketS2COuterClass.WrappedPacketS2C
                    .newBuilder()
                    .setHandShake(HandShakePacketS2COuterClass.HandShakePacketS2C
                            .newBuilder()
                            .setId(SharedConstants.HAND_SHAKE_ID_VALUE)
                            .build())
                    .build());
            client.setHandShaked(true);
            client.flush();
        } else {
            client.disconnect();
        }
    }

    public static void registerServer(RegisterServerPacketC2SOuterClass.RegisterServerPacketC2S packet, Client client) {
        if (!client.isHandShaked()) {
            log.warn("Client tried to do something while not handshaken, discarding.");
            return;
        }
        ServerOperationsHandler.getInstance().addServer(packet.getAddress(), packet.getPort(), packet.getType());
    }

    public static void unregisterServer(UnregisterServerPacketC2SOuterClass.UnregisterServerPacketC2S packet, Client client) {
        ServerOperationsHandler.getInstance().removeServer(packet.getIp(), packet.getPort());
    }

    public static void transferPlayer(TransferPlayerPacketC2SOuterClass.TransferPlayerPacketC2S packet, Client client) {
        UUID uuid = UUID.fromString(packet.getUuid());
        ServerOperationsHandler.getInstance().transferPlayer(uuid, packet.getType());
    }
}
