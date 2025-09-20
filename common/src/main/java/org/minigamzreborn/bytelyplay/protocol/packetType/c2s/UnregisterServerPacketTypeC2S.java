package org.minigamzreborn.bytelyplay.protocol.packetType.c2s;

import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.UnregisterServerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protocol.packetType.PacketTypeC2S;
import org.minigamzreborn.bytelyplay.protocol.utils.Client;

import java.util.function.BiConsumer;

public class UnregisterServerPacketTypeC2S extends PacketTypeC2S<UnregisterServerPacketC2SOuterClass.UnregisterServerPacketC2S> {
    public UnregisterServerPacketTypeC2S(BiConsumer<UnregisterServerPacketC2SOuterClass.UnregisterServerPacketC2S, Client> handler) {
        super(UnregisterServerPacketC2SOuterClass.UnregisterServerPacketC2S.parser(), handler, UnregisterServerPacketC2SOuterClass.UnregisterServerPacketC2S.class);
    }

    @Override
    public boolean isWrappedPacketThis(WrappedPacketC2SOuterClass.WrappedPacketC2S packetS2C) {
        return packetS2C.hasUnregisterServerPacket();
    }

    @Override
    public void receivedPacketWrapped(WrappedPacketC2SOuterClass.WrappedPacketC2S packet, Client client) {
        receivedPacket(packet.getUnregisterServerPacket(), client);
    }
}
