package org.minigamzreborn.bytelyplay.protocol.utils;

import io.netty.channel.socket.SocketChannel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.DisconnectPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.s2c.DisconnectPacketS2COuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.s2c.WrappedPacketS2COuterClass;

// represents a client
@Slf4j
public class Client {
    private final SocketChannel channel;
    @Getter
    @Setter
    private boolean handShaked = false;
    // DO NOT set manually, please call disconnect otherwise it can break things
    @Getter
    @Setter
    private boolean disconnected = false;

    public Client(@NotNull SocketChannel channel) {
        this.channel = channel;
    }

    public void sendPacket(WrappedPacketS2COuterClass.WrappedPacketS2C packet) {
        channel.write(packet);
        if (channel.isActive() && handShaked) channel.flush();
    }

    public void disconnect() {
        if (disconnected) {
            channel.close();
            return;
        }

        this.sendPacket(
                WrappedPacketS2COuterClass.WrappedPacketS2C.newBuilder().setDisconnectPacket(
                        DisconnectPacketS2COuterClass.DisconnectPacketS2C.newBuilder().build()
                ).build()
        );
        channel.close();
        disconnected = true;
    }

    public void flush() {
        if (handShaked) {
            channel.flush();
        } else {
            log.warn("Tried to server tried to flush client but isn't handshaken...");
        }
    }
}
