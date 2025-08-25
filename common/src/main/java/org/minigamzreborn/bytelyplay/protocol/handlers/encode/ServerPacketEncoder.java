package org.minigamzreborn.bytelyplay.protocol.handlers.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketS2COuterClass;
import org.minigamzreborn.bytelyplay.protocol.Constants.SharedConstants;

public class ServerPacketEncoder extends MessageToByteEncoder<WrappedPacketS2COuterClass.WrappedPacketS2C> {
    @Override
    protected void encode(ChannelHandlerContext ctx, WrappedPacketS2COuterClass.WrappedPacketS2C packet, ByteBuf byteBuf) {
        byteBuf.writeBytes(packet.toByteArray());
        byteBuf.writeBytes(SharedConstants.PACKET_DELIMITER);
    }
}
