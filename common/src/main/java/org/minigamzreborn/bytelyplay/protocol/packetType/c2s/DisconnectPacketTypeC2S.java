package org.minigamzreborn.bytelyplay.protocol.packetType.c2s;

import com.google.protobuf.Parser;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.DisconnectPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protocol.packetType.PacketTypeC2S;
import org.minigamzreborn.bytelyplay.protocol.utils.Client;

import java.util.function.BiConsumer;

public class DisconnectPacketTypeC2S extends PacketTypeC2S<DisconnectPacketC2SOuterClass.DisconnectPacketC2S> {
    public DisconnectPacketTypeC2S(BiConsumer<DisconnectPacketC2SOuterClass.DisconnectPacketC2S, Client> handler) {
        super(DisconnectPacketC2SOuterClass.DisconnectPacketC2S.parser(),
                handler,
                DisconnectPacketC2SOuterClass.DisconnectPacketC2S.class);
    }

    @Override
    public boolean isWrappedPacketThis(WrappedPacketC2SOuterClass.WrappedPacketC2S packetS2C) {
        return packetS2C.hasDisconnectPacket();
    }

    @Override
    public void receivedPacketWrapped(WrappedPacketC2SOuterClass.WrappedPacketC2S packet, Client client) {
        receivedPacket(packet.getDisconnectPacket(), client);
    }
}
