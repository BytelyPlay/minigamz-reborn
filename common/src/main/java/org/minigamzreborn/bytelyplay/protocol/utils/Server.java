package org.minigamzreborn.bytelyplay.protocol.utils;

import io.netty.channel.socket.SocketChannel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.DisconnectPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.TransferPlayerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.WrappedPacketC2SOuterClass;

import java.util.UUID;

@Slf4j
public class Server {
    private final SocketChannel channel;
    @Getter
    @Setter
    private boolean handShaked = false;
    // DO NOT set manually, please call disconnect otherwise it can break things
    @Getter
    @Setter
    private boolean disconnected = false;

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
        if (disconnected) {
            channel.close();
            return;
        }

        this.sendPacket(WrappedPacketC2SOuterClass.WrappedPacketC2S.newBuilder()
                .setDisconnectPacket(
                        DisconnectPacketC2SOuterClass.DisconnectPacketC2S.newBuilder().build()
                )
                .build());
        channel.close();
        disconnected = true;
    }

    public void flush() {
        if (isHandShaked()) {
            channel.flush();
        } else {
            log.warn("Tried to flush while not handshaken.");
        }
    }

    public void sendPlayerToServer(UUID uuid, ServerTypeOuterClass.ServerType type) {
        this.sendPacket(WrappedPacketC2SOuterClass.WrappedPacketC2S.newBuilder()
                .setTransferPlayerPacket(TransferPlayerPacketC2SOuterClass.TransferPlayerPacketC2S.newBuilder()
                        .setType(type)
                        .setUuid(uuid.toString())
                        .build())
                .build());
    }
}
