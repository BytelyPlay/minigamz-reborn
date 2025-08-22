package org.minigamzreborn.bytelyplay.protocol;

import org.minigamzreborn.bytelyplay.protocol.CompletionHandlers.ServerCompletionHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class ProtoServer {
    public void init() {
        try {
            AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress("0.0.0.0", 8594));;
            serverChannel.accept(serverChannel, new ServerCompletionHandler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
