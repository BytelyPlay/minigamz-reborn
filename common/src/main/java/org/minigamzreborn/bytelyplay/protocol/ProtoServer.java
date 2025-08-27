package org.minigamzreborn.bytelyplay.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import org.minigamzreborn.bytelyplay.protocol.Constants.SharedConstants;
import org.minigamzreborn.bytelyplay.protocol.handlers.encode.ServerPacketEncoder;
import org.minigamzreborn.bytelyplay.protocol.handlers.logic.ServerLogicHandler;
import org.minigamzreborn.bytelyplay.protocol.handlers.decode.ServerPacketDecoder;
import org.minigamzreborn.bytelyplay.protocol.utils.Client;
import org.minigamzreborn.bytelyplay.protocol.utils.CloseFutureListener;

import java.net.InetSocketAddress;

public class ProtoServer {
    public void init(String ip, int port) {
        EventLoopGroup bossGroup = new MultiThreadIoEventLoopGroup(1, NioIoHandler.newFactory());
        EventLoopGroup workerGroup = new MultiThreadIoEventLoopGroup(2, NioIoHandler.newFactory());

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(getInitializer())
                .option(ChannelOption.SO_BACKLOG, 16)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture acceptConnections = bootstrap.bind(new InetSocketAddress(ip, port));
        acceptConnections.channel().closeFuture().addListener(new CloseFutureListener(bossGroup, workerGroup));
    }
    private void setupChannel(SocketChannel channel) {
        Client client = new Client(channel);
        channel.pipeline().addLast(
                new DelimiterBasedFrameDecoder(1024, SharedConstants.PACKET_DELIMITER),
                new ServerPacketDecoder(),
                new ServerLogicHandler(client),
                new ServerPacketEncoder()
        );
    }
    private ChannelInitializer<SocketChannel> getInitializer() {
        return new ChannelInitializer<>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                setupChannel(ch);
            }
        };
    }
}
