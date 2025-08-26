package org.minigamzreborn.bytelyplay.protocol.utils;

import io.netty.channel.socket.SocketChannel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketS2COuterClass;

public class Server {
    private final SocketChannel channel;
    @Getter @Setter
    private boolean handShaked = false;
    public Server(@NotNull SocketChannel channel) {
        this.channel = channel;
    }

    public void sendPacket(WrappedPacketC2SOuterClass.WrappedPacketC2S packet) {
        channel.write(packet);
    }
    public void disconnect() {
        // TODO: send disconnect packet
        channel.close();
    }
}
