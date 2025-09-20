package org.minigamzreborn.bytelyplay.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.minigamzreborn.bytelyplay.protocol.pipeline.decode.ServerPacketDecoder;
import org.minigamzreborn.bytelyplay.protocol.pipeline.encode.ServerPacketEncoder;
import org.minigamzreborn.bytelyplay.protocol.pipeline.logic.ServerLogicHandler;
import org.minigamzreborn.bytelyplay.protocol.utils.Client;
import org.minigamzreborn.bytelyplay.protocol.utils.CloseEventLoopFutureListener;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.Future;

public class ProtoServer {
    private final ArrayList<Client> connectedClients = new ArrayList<>();
    private Channel serverChannel;
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
        serverChannel = acceptConnections.channel();

        serverChannel.closeFuture().addListener(new CloseEventLoopFutureListener(bossGroup, workerGroup));
    }
    private void setupChannel(SocketChannel channel) {
        Client client = new Client(channel);
        channel.pipeline().addLast(
                new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4),
                new ServerPacketDecoder(),
                new ServerLogicHandler(client),
                new ServerPacketEncoder()
        );
        connectedClients.add(client);
    }
    private ChannelInitializer<SocketChannel> getInitializer() {
        return new ChannelInitializer<>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                setupChannel(ch);
            }
        };
    }
    public void shutdown() {
        connectedClients.stream()
                .filter(client -> !client.isDisconnected())
                .forEach(Client::disconnect);
        connectedClients.clear();

        serverChannel.close();
    }
}
