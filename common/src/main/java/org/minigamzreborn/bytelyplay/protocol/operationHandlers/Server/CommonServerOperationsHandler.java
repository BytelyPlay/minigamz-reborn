package org.minigamzreborn.bytelyplay.protocol.operationHandlers.Server;

import lombok.Getter;
import org.minigamzreborn.bytelyplay.protobuffer.packets.HandShakePacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.HandShakePacketS2COuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.RegisterServerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketS2COuterClass;
import org.minigamzreborn.bytelyplay.protocol.Client;

public class CommonServerOperationsHandler {
    @Getter
    private static CommonServerOperationsHandler instance;

    public static void handShake(HandShakePacketC2SOuterClass.HandShakePacketC2S packet, Client client) {
        if (packet.getId() == 8439) {
            client.sendPacket(WrappedPacketS2COuterClass.WrappedPacketS2C
                    .newBuilder()
                    .setHandShake(HandShakePacketS2COuterClass.HandShakePacketS2C
                            .newBuilder()
                            .setId(8439)
                            .build())
                    .build());
        }
    }

    public static void registerServer(RegisterServerPacketC2SOuterClass.RegisterServerPacketC2S packet, Client client) {
        ServerOperationsHandler.getInstance().addServer(packet.getAddress(), packet.getPort(), packet.getType());
    }
}
