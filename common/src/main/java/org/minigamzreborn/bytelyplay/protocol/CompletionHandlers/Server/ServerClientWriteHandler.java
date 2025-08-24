package org.minigamzreborn.bytelyplay.protocol.CompletionHandlers.Server;

import org.minigamzreborn.bytelyplay.protocol.wrappers.ServerWriteAttachment;

import java.nio.channels.CompletionHandler;

public class ServerClientWriteHandler implements CompletionHandler<Integer, ServerWriteAttachment> {
    @Override
    public void completed(Integer result, ServerWriteAttachment attachment) {

    }

    @Override
    public void failed(Throwable exc, ServerWriteAttachment attachment) {
        exc.printStackTrace();
    }
}
