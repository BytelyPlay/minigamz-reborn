package org.minigamzreborn.bytelyplay.protocol.packetType.s2c;

import org.minigamzreborn.bytelyplay.protobuffer.packets.s2c.HandShakePacketS2COuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.s2c.WrappedPacketS2COuterClass;
import org.minigamzreborn.bytelyplay.protocol.packetType.PacketTypeS2C;
import org.minigamzreborn.bytelyplay.protocol.utils.Server;

import java.util.function.BiConsumer;

public class HandShakePacketTypeS2C extends PacketTypeS2C<HandShakePacketS2COuterClass.HandShakePacketS2C> {
    public HandShakePacketTypeS2C(BiConsumer<HandShakePacketS2COuterClass.HandShakePacketS2C, Server> handler) {
        super(HandShakePacketS2COuterClass.HandShakePacketS2C.parser(), handler, HandShakePacketS2COuterClass.HandShakePacketS2C.class);
    }

    @Override
    public boolean isWrappedPacketThis(WrappedPacketS2COuterClass.WrappedPacketS2C packetS2C) {
        return packetS2C.hasHandShake();
    }

    @Override
    public void receivedPacketWrapped(WrappedPacketS2COuterClass.WrappedPacketS2C packet, Server server) {
        receivedPacket(packet.getHandShake(), server);
    }
}
