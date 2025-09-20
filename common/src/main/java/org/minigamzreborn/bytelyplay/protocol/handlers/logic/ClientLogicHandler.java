package org.minigamzreborn.bytelyplay.protocol.handlers.logic;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.HandShakePacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.s2c.WrappedPacketS2COuterClass;
import org.minigamzreborn.bytelyplay.protocol.Constants.SharedConstants;
import org.minigamzreborn.bytelyplay.protocol.packetType.PacketTypeS2C;
import org.minigamzreborn.bytelyplay.protocol.utils.Packets;
import org.minigamzreborn.bytelyplay.protocol.utils.Server;

@Slf4j
public class ClientLogicHandler extends SimpleChannelInboundHandler<WrappedPacketS2COuterClass.WrappedPacketS2C> {
    private final Server server;
    public ClientLogicHandler(Server server) {
        this.server = server;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WrappedPacketS2COuterClass.WrappedPacketS2C packet) throws Exception {
        for (PacketTypeS2C<?> packetTypeC2S : Packets.getS2CPackets()) {
            if (packetTypeC2S.isWrappedPacketThis(packet)) {
                packetTypeC2S.receivedPacketWrapped(packet, server);
                return;
            }
        }
        log.error("Received unknown packet?");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        server.sendPacket(
                WrappedPacketC2SOuterClass.WrappedPacketC2S
                        .newBuilder()
                        .setHandShake(HandShakePacketC2SOuterClass.HandShakePacketC2S
                                .newBuilder()
                                .setId(SharedConstants.HAND_SHAKE_ID_VALUE)
                                .build())
                        .build()
        );
        ctx.channel().flush();
    }
}
