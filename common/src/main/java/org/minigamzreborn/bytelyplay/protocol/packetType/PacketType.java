package org.minigamzreborn.bytelyplay.protocol.packetType;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;

public abstract class PacketType<T extends GeneratedMessage> {
    private final Parser<T> parser;

    public PacketType(Parser<T> parser) {
        this.parser = parser;
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
    public abstract void receivedPacket(T packet);
}
