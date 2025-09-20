package org.minigamzreborn.bytelyplay.protocol.packetType.s2c;

import com.google.protobuf.Parser;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.DisconnectPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.s2c.DisconnectPacketS2COuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.s2c.WrappedPacketS2COuterClass;
import org.minigamzreborn.bytelyplay.protocol.packetType.PacketTypeS2C;
import org.minigamzreborn.bytelyplay.protocol.utils.Server;

import java.util.function.BiConsumer;

public class DisconnectPacketTypeS2C extends PacketTypeS2C<DisconnectPacketS2COuterClass.DisconnectPacketS2C> {
    public DisconnectPacketTypeS2C(BiConsumer<DisconnectPacketS2COuterClass.DisconnectPacketS2C,
            Server> handler) {
        super(DisconnectPacketS2COuterClass.DisconnectPacketS2C.parser(),
                handler,
                DisconnectPacketS2COuterClass.DisconnectPacketS2C.class);
    }

    @Override
    public boolean isWrappedPacketThis(WrappedPacketS2COuterClass.WrappedPacketS2C packetS2C) {
        return packetS2C.hasDisconnectPacket();
    }

    @Override
    public void receivedPacketWrapped(WrappedPacketS2COuterClass.WrappedPacketS2C packet, Server server) {
        receivedPacket(packet.getDisconnectPacket(), server);
    }
}
