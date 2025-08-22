package org.minigamzreborn.bytelyplay.protocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class ProtoServer {
    public void init() {
        try {
            AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress("0.0.0.0", 8594));
            serverChannel.accept(null, new CompletionHandler<>() {
                @Override
                public void completed(AsynchronousSocketChannel result, Object attachment) {

                }

                @Override
                public void failed(Throwable exc, Object attachment) {

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
