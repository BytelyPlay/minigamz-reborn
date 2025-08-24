package org.minigamzreborn.bytelyplay.protocol.CompletionHandlers.Server;

import org.minigamzreborn.bytelyplay.protocol.Client;
import org.minigamzreborn.bytelyplay.protocol.wrappers.ServerReadAttachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ServerClientCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {
    private static final Logger log = LoggerFactory.getLogger(ServerClientCompletionHandler.class);

    @Override
    public void completed(AsynchronousSocketChannel result, Void v) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            ServerReadAttachment attachment = new ServerReadAttachment();
            attachment.buffer = buffer;
            attachment.clientChannel = result;
            ServerClientReadHandler readHandler = new ServerClientReadHandler();
            result.read(buffer, attachment, new ServerClientReadHandler());
            attachment.client = new Client(readHandler, attachment);
            log.info("Client successfully connected: {}", result.getRemoteAddress().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, Void attachment) {
        exc.printStackTrace();
    }
}
