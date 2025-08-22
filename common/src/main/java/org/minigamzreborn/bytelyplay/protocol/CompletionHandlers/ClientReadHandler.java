package org.minigamzreborn.bytelyplay.protocol.CompletionHandlers;

import com.google.protobuf.InvalidProtocolBufferException;
import org.minigamzreborn.bytelyplay.protobuffer.packets.PacketWrapperOuterClass;
import org.minigamzreborn.bytelyplay.protocol.wrappers.ClientReadAttachment;

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;

public class ClientReadHandler implements CompletionHandler<Integer, ClientReadAttachment> {
    public static ArrayList<PacketWrapperOuterClass.PacketWrapper> fullPacketWrappers = new ArrayList<>();
    @Override
    public void completed(Integer size, ClientReadAttachment attachment) {
        byte[] lengthPrefix = new byte[4];
        if (size >= 4) {
            if (attachment.toRead == -1) {
                attachment.buffer.get(lengthPrefix);
                attachment.toRead = ByteBuffer.wrap(lengthPrefix).getInt();
                attachment.buffer.compact();
                if (!(attachment.toRead >= 1 && attachment.toRead <= 10240)) {
                    attachment.toRead = 0;
                    throw new StackOverflowError("buffer length is not in a valid range.");
                }
            }
            return;
        }
        if (attachment.toRead == -1) {
            read(attachment);
            return;
        }
        if (attachment.toRead == 0) {
            byte[] bytes = new byte[attachment.buffer.remaining()];
            try {
                PacketWrapperOuterClass.PacketWrapper wrapper = PacketWrapperOuterClass.PacketWrapper
                        .parseFrom(bytes);
                fullPacketWrappers.add(wrapper);
            } catch (InvalidProtocolBufferException e) {
                System.out.println("Sent invalid PacketWrapper...");
                e.printStackTrace();
            }
        }
    }
    @Override
    public void failed(Throwable exc, ClientReadAttachment buffer) {

    }
    private void read(ClientReadAttachment attachment) {
        attachment.client.read(attachment.buffer, attachment, this);
    }
}
