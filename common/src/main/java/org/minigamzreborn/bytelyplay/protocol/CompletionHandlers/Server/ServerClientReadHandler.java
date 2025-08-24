package org.minigamzreborn.bytelyplay.protocol.CompletionHandlers.Server;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protocol.wrappers.ServerReadAttachment;

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;

@Slf4j
public class ServerClientReadHandler implements CompletionHandler<Integer, ServerReadAttachment> {
    @Override
    public void completed(Integer size, ServerReadAttachment attachment) {
        byte[] lengthPrefix = new byte[4];
        if (size >= 4) {
            if (attachment.toRead == -1) {
                attachment.buffer.get(lengthPrefix);
                attachment.toRead = ByteBuffer.wrap(lengthPrefix).getInt();
                attachment.buffer.compact();
                if (!(attachment.toRead >= 1 && attachment.toRead <= 10240)) {
                    attachment.toRead = -1;
                    read(attachment);
                    throw new StackOverflowError("buffer length is not in a valid range.");
                }
            }
        }
        if (attachment.toRead == -1) {
            try {
                attachment.clientChannel.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        if (attachment.toRead == 0) {
            byte[] bytes = new byte[attachment.buffer.remaining()];
            attachment.buffer.get(bytes);
            try {
                WrappedPacketC2SOuterClass.WrappedPacketC2S wrappedPacket = WrappedPacketC2SOuterClass.WrappedPacketC2S
                        .parseFrom(bytes);
                attachment.client.receivePacket(wrappedPacket);
                attachment.toRead = -1;
            } catch (InvalidProtocolBufferException e) {
                log.info("Sent invalid WrappedPacket...");
                e.printStackTrace();
            }
        }
        read(attachment);
    }
    @Override
    public void failed(Throwable exc, ServerReadAttachment buffer) {

    }
    private void read(ServerReadAttachment attachment) {
        attachment.clientChannel.read(attachment.buffer, attachment, this);
    }
}
