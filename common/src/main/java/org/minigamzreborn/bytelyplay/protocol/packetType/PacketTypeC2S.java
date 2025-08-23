package org.minigamzreborn.bytelyplay.protocol.packetType;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Parser;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketS2COuterClass;

public abstract class PacketTypeC2S<T extends GeneratedMessage> extends PacketType<T> {
    public PacketTypeC2S(Parser<T> parser) {
        super(parser);
    }
    public abstract boolean isWrappedPacketThis(WrappedPacketC2SOuterClass.WrappedPacketC2S packetS2C);
}
