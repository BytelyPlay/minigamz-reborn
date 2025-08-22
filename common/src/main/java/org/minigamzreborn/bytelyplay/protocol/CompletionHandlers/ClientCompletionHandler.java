package org.minigamzreborn.bytelyplay.protocol.CompletionHandlers;

import org.minigamzreborn.bytelyplay.protocol.wrappers.ClientReadAttachment;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ClientCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {
    @Override
    public void completed(AsynchronousSocketChannel result, Void v) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            ClientReadAttachment attachment = new ClientReadAttachment();
            attachment.buffer = buffer;
            attachment.client = result;
            result.read(buffer, attachment, new ClientReadHandler());
            System.out.println("Client successfully connected: " + result.getRemoteAddress().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, Void attachment) {
        exc.printStackTrace();
    }
}
