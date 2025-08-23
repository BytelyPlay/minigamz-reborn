package org.minigamzreborn.bytelyplay.protocol.packetType;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Parser;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketS2COuterClass;

public abstract class PacketTypeS2C<T extends GeneratedMessage> extends PacketType<T> {
    public PacketTypeS2C(Parser<T> parser) {
        super(parser);
    }
    public abstract boolean isWrappedPacketThis(WrappedPacketS2COuterClass.WrappedPacketS2C packetS2C);
}
