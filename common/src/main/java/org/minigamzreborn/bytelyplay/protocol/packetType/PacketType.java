package org.minigamzreborn.bytelyplay.protocol.packetType;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.function.BiConsumer;

// V represents the client or server class or more so the connection class in a way... T represents the packet
public abstract class PacketType<T extends GeneratedMessage, V> {
    protected final Parser<T> parser;
    protected final BiConsumer<T, V> handler;
    // just for when we need it...
    protected final Class<T> packetDataClass;

    public PacketType(Parser<T> parser, BiConsumer<T, V> handler, Class<T> packetDataClass) {
        this.parser = parser;
        this.handler = handler;
        this.packetDataClass = packetDataClass;
    }

    public ByteBuffer encode(T packet) {
        return ByteBuffer.wrap(packet.toByteArray());
    }
    public @Nullable T decode(ByteBuffer buffer) {
        try {
            return parser.parseFrom(buffer);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void receivedPacket(T packet, V client) {
        handler.accept(packet, client);
    }
}
