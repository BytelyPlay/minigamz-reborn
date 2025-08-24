package org.minigamzreborn.bytelyplay.protocol.packetType.c2s;

import com.google.protobuf.Parser;
import org.minigamzreborn.bytelyplay.protobuffer.packets.HandShakePacketS2COuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketS2COuterClass;
import org.minigamzreborn.bytelyplay.protocol.Client;
import org.minigamzreborn.bytelyplay.protocol.packetType.PacketTypeS2C;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class HandShakePacketTypeS2C extends PacketTypeS2C<HandShakePacketS2COuterClass.HandShakePacketS2C> {
    public HandShakePacketTypeS2C(BiConsumer<HandShakePacketS2COuterClass.HandShakePacketS2C, Client> handler) {
        super(HandShakePacketS2COuterClass.HandShakePacketS2C.parser(), handler, HandShakePacketS2COuterClass.HandShakePacketS2C.class);
    }

    @Override
    public boolean isWrappedPacketThis(WrappedPacketS2COuterClass.WrappedPacketS2C packetS2C) {
        return packetS2C.hasHandShake();
    }

    @Override
    public void receivedPacketWrapped(WrappedPacketS2COuterClass.WrappedPacketS2C packet) {
        receivedPacket(packet.getHandShake());
    }
}
