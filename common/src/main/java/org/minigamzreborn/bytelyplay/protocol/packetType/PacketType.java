package org.minigamzreborn.bytelyplay.protocol.packetType;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

public abstract class PacketType<T extends GeneratedMessage> {
    protected final Parser<T> parser;
    protected final Consumer<T> handler;
    // just for when we need it...
    protected final Class<T> packetDataClass;

    public PacketType(Parser<T> parser, Consumer<T> handler, Class<T> packetDataClass) {
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
    public void receivedPacket(T packet) {
        handler.accept(packet);
    }
}
