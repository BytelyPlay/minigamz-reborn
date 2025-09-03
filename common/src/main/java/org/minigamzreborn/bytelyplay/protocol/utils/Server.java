package org.minigamzreborn.bytelyplay.protocol.utils;

import io.netty.channel.socket.SocketChannel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;

@Slf4j
public class Server {
    private final SocketChannel channel;
    @Getter @Setter
    private boolean handShaked = false;
    public Server(@NotNull SocketChannel channel) {
        this.channel = channel;
    }

    public void sendPacket(WrappedPacketC2SOuterClass.WrappedPacketC2S packet) {
        channel.write(packet);
        if (channel.isActive() && handShaked) {
            channel.flush();
        }
    }
    public void disconnect() {
        // TODO: send disconnect packet
        channel.close();
    }
    public void flush() {
        if (isHandShaked()) {
            channel.flush();
        } else {
            log.warn("Tried to flush while not handshaken.");
        }
    }
}
