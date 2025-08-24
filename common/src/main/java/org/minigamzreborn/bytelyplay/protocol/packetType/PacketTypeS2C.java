package org.minigamzreborn.bytelyplay.protocol.packetType;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Parser;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketS2COuterClass;
import org.minigamzreborn.bytelyplay.protocol.Client;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class PacketTypeS2C<T extends GeneratedMessage> extends PacketType<T> {
    public PacketTypeS2C(Parser<T> parser, BiConsumer<T, Client> handler, Class<T> packetDataClass) {
        super(parser, handler, packetDataClass);
    }
    public abstract boolean isWrappedPacketThis(WrappedPacketS2COuterClass.WrappedPacketS2C packetS2C);
    public abstract void receivedPacketWrapped(WrappedPacketS2COuterClass.WrappedPacketS2C packet, Client client);
}
