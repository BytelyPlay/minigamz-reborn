package org.minigamzreborn.bytelyplay.protocol;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketS2COuterClass;
import org.minigamzreborn.bytelyplay.protocol.CompletionHandlers.ServerClientReadHandler;
import org.minigamzreborn.bytelyplay.protocol.CompletionHandlers.ServerClientWriteHandler;
import org.minigamzreborn.bytelyplay.protocol.wrappers.ServerReadAttachment;
import org.minigamzreborn.bytelyplay.protocol.wrappers.ServerWriteAttachment;

import java.io.IOException;
import java.nio.ByteBuffer;

// represents a client
public class Client {
    @Getter
    private final ServerClientReadHandler serverClientReadHandler;
    @Getter
    private final ServerReadAttachment attachment;

    public Client(@NotNull ServerClientReadHandler readHandler, @NotNull ServerReadAttachment attachment) {
        this.serverClientReadHandler = readHandler;
        this.attachment = attachment;
    }

    public void sendPacket(WrappedPacketS2COuterClass.WrappedPacketS2C packet) {
        ByteBuffer toSend = ByteBuffer.wrap(packet.toByteArray());
        attachment.clientChannel.write(toSend, new ServerWriteAttachment(), new ServerClientWriteHandler());
    }
    public void receivePacket(WrappedPacketC2SOuterClass.WrappedPacketC2S packet) {

    }
    public void disconnect() {
        // TODO: send disconnect packet
        try {
            attachment.clientChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
