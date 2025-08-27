package org.minigamzreborn.bytelyplay.protocol.utils;

import io.netty.channel.socket.SocketChannel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketS2COuterClass;

// represents a client
@Slf4j
public class Client {
    private final SocketChannel channel;
    @Getter @Setter
    private boolean handShaked = false;
    public Client(@NotNull SocketChannel channel) {
        this.channel = channel;
    }

    public void sendPacket(WrappedPacketS2COuterClass.WrappedPacketS2C packet) {
        channel.write(packet);
        if (channel.isActive()) channel.flush();
    }
    public void disconnect() {
        // TODO: send disconnect packet
        channel.close();
    }
}
