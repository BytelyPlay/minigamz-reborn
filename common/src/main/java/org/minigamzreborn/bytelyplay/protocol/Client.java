package org.minigamzreborn.bytelyplay.protocol;

import io.netty.channel.socket.SocketChannel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketS2COuterClass;
import org.minigamzreborn.bytelyplay.protocol.Constants.SharedConstants;
import org.minigamzreborn.bytelyplay.protocol.packetType.PacketTypeC2S;

import java.io.IOException;
import java.nio.ByteBuffer;

// represents a client
@Slf4j
public class Client {
    private final SocketChannel channel;

    public Client(@NotNull SocketChannel channel) {
        this.channel = channel;
    }

    public void sendPacket(WrappedPacketS2COuterClass.WrappedPacketS2C packet) {
        channel.write(packet);
    }
    public void disconnect() {
        // TODO: send disconnect packet
        channel.close();
    }
}
