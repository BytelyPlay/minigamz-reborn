package org.minigamzreborn.bytelyplay.protocol.packetType.c2s;

import com.google.protobuf.Parser;
import org.minigamzreborn.bytelyplay.protobuffer.packets.HandShakePacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protocol.packetType.PacketType;
import org.minigamzreborn.bytelyplay.protocol.packetType.PacketTypeC2S;

import java.util.function.Consumer;

public class HandShakePacketTypeC2S extends PacketTypeC2S<HandShakePacketC2SOuterClass.HandShakePacketC2S> {
    public HandShakePacketTypeC2S(Consumer<HandShakePacketC2SOuterClass.HandShakePacketC2S> handler) {
        super(HandShakePacketC2SOuterClass.HandShakePacketC2S.parser(), handler, HandShakePacketC2SOuterClass.HandShakePacketC2S.class);
    }

    @Override
    public boolean isWrappedPacketThis(WrappedPacketC2SOuterClass.WrappedPacketC2S packetS2C) {
        return packetS2C.hasHandShake();
    }

    @Override
    public void receivedPacketWrapped(WrappedPacketC2SOuterClass.WrappedPacketC2S packet) {
        receivedPacket(packet.getHandShake());
    }
}
