package org.minigamzreborn.bytelyplay.protocol.packetType;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Parser;
import lombok.extern.slf4j.Slf4j;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketS2COuterClass;

import java.util.function.Consumer;

@Slf4j
public abstract class PacketTypeC2S<T extends GeneratedMessage> extends PacketType<T> {
    public PacketTypeC2S(Parser<T> parser, Consumer<T> handler, Class<T> packetDataClass) {
        super(parser, handler, packetDataClass);
    }
    public abstract boolean isWrappedPacketThis(WrappedPacketC2SOuterClass.WrappedPacketC2S packetS2C);
    public abstract void receivedPacketWrapped(WrappedPacketC2SOuterClass.WrappedPacketC2S packet);
}
