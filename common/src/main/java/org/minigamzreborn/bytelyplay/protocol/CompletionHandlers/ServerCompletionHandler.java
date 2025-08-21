package org.minigamzreborn.bytelyplay.protocol.CompletionHandlers;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Object> {
    @Override
    public void completed(AsynchronousSocketChannel result, Object attachment) {

    }

    @Override
    public void failed(Throwable exc, Object attachment) {
        exc.printStackTrace();
    }
}
