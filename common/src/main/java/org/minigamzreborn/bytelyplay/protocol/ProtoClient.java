package org.minigamzreborn.bytelyplay.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.Getter;
import org.minigamzreborn.bytelyplay.protocol.pipeline.decode.ClientPacketDecoder;
import org.minigamzreborn.bytelyplay.protocol.pipeline.encode.ClientPacketEncoder;
import org.minigamzreborn.bytelyplay.protocol.pipeline.logic.ClientLogicHandler;
import org.minigamzreborn.bytelyplay.protocol.utils.CloseEventLoopFutureListener;
import org.minigamzreborn.bytelyplay.protocol.utils.Server;

import java.net.InetSocketAddress;

public class ProtoClient {
    @Getter
    private Server server;
    public Server init(String ip, int port) {
        EventLoopGroup workerGroup = new MultiThreadIoEventLoopGroup(2, NioIoHandler.newFactory());
        Bootstrap bootstrap = new Bootstrap()
                .channel(NioSocketChannel.class)
                .group(workerGroup)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(getInitializer());
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(ip, port));
        future.channel().closeFuture().addListener(new CloseEventLoopFutureListener(workerGroup));
        try {
            future.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return server;
    }
    private void setupChannel(SocketChannel channel) {
        Server server = new Server(channel);
        channel.pipeline().addLast(
                new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4),
                new ClientPacketDecoder(),
                new ClientLogicHandler(server),
                new ClientPacketEncoder()
        );
        this.server = server;
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
