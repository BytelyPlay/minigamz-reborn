package org.minigamzreborn.bytelyplay.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import lombok.Getter;
import org.minigamzreborn.bytelyplay.protocol.Constants.SharedConstants;
import org.minigamzreborn.bytelyplay.protocol.handlers.decode.ClientPacketDecoder;
import org.minigamzreborn.bytelyplay.protocol.handlers.decode.ServerPacketDecoder;
import org.minigamzreborn.bytelyplay.protocol.handlers.encode.ClientPacketEncoder;
import org.minigamzreborn.bytelyplay.protocol.handlers.encode.ServerPacketEncoder;
import org.minigamzreborn.bytelyplay.protocol.handlers.logic.ClientLogicHandler;
import org.minigamzreborn.bytelyplay.protocol.handlers.logic.ServerLogicHandler;
import org.minigamzreborn.bytelyplay.protocol.utils.Client;
import org.minigamzreborn.bytelyplay.protocol.utils.CloseFutureListener;
import org.minigamzreborn.bytelyplay.protocol.utils.Server;

import java.net.InetSocketAddress;

public class ProtoClient {
    @Getter
    private Server server;
    public Server init(String ip, int port) {
        EventLoopGroup workerGroup = new MultiThreadIoEventLoopGroup(2, NioIoHandler.newFactory());
        Bootstrap bootstrap = new Bootstrap()
                .channel(NioServerSocketChannel.class)
                .group(workerGroup)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(getInitializer());
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(ip, port));
        future.addListener(new CloseFutureListener(workerGroup));
        return server;
    }
    private void setupChannel(SocketChannel channel) {
        Server server = new Server(channel);
        channel.pipeline().addLast(
                new DelimiterBasedFrameDecoder(1024, SharedConstants.PACKET_DELIMITER),
                new ClientPacketEncoder(),
                new ClientPacketDecoder(),
                new ClientLogicHandler(server)
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
