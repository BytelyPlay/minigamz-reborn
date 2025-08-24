package org.minigamzreborn.bytelyplay.protocol.packetType.c2s;

import com.google.protobuf.Parser;
import org.minigamzreborn.bytelyplay.protobuffer.packets.RegisterServerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protocol.packetType.PacketTypeC2S;

import java.util.function.Consumer;

public class RegisterServerPacketTypeC2S extends PacketTypeC2S<RegisterServerPacketC2SOuterClass.RegisterServerPacketC2S> {
    public RegisterServerPacketTypeC2S(Consumer<RegisterServerPacketC2SOuterClass.RegisterServerPacketC2S> handler) {
        super(RegisterServerPacketC2SOuterClass.RegisterServerPacketC2S.parser(), handler, RegisterServerPacketC2SOuterClass.RegisterServerPacketC2S.class);
    }

    @Override
    public boolean isWrappedPacketThis(WrappedPacketC2SOuterClass.WrappedPacketC2S packetS2C) {
        return packetS2C.hasRegisterServerPacket();
    }

    @Override
    public void receivedPacketWrapped(WrappedPacketC2SOuterClass.WrappedPacketC2S packet) {
        receivedPacket(packet.getRegisterServerPacket());
    }
}
