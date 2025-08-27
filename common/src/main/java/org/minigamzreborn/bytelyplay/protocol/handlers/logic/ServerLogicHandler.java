package org.minigamzreborn.bytelyplay.protocol.handlers.logic;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protocol.utils.Client;
import org.minigamzreborn.bytelyplay.protocol.utils.Packets;
import org.minigamzreborn.bytelyplay.protocol.packetType.PacketTypeC2S;

@Slf4j
public class ServerLogicHandler extends SimpleChannelInboundHandler<WrappedPacketC2SOuterClass.WrappedPacketC2S> {
    private final Client client;
    public ServerLogicHandler(Client client) {
        this.client = client;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                WrappedPacketC2SOuterClass.WrappedPacketC2S packet) {
        if (!client.isHandShaked()) {
            log.warn("Client tried to send a packet while not handshaken.");
            return;
        }
        for (PacketTypeC2S<?> packetTypeC2S : Packets.getC2SPackets()) {
            if (packetTypeC2S.isWrappedPacketThis(packet)) {
                packetTypeC2S.receivedPacketWrapped(packet, client);
                System.out.println("Received packet... server");
                return;
            }
        }
        log.error("Received unknown packet?");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.channel().flush();
        System.out.println("server flushed");
    }
}
