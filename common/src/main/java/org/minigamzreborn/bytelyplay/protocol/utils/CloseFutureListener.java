package org.minigamzreborn.bytelyplay.protocol.utils;

import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.List;

public class CloseFutureListener implements GenericFutureListener<ChannelFuture> {
    private final List<EventLoopGroup> closeOnShutdown;
    public CloseFutureListener(EventLoopGroup... toCloseOnShutdown) {
        closeOnShutdown = List.of(toCloseOnShutdown);
    }
    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        closeOnShutdown.forEach(EventExecutorGroup::shutdownGracefully);
    }
}
