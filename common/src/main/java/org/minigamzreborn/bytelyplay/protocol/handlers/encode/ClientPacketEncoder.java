package org.minigamzreborn.bytelyplay.protocol.handlers.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protocol.Constants.SharedConstants;

public class ClientPacketEncoder extends MessageToByteEncoder<WrappedPacketC2SOuterClass.WrappedPacketC2S> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, WrappedPacketC2SOuterClass.WrappedPacketC2S packetC2S, ByteBuf byteBuf) throws Exception {
        byteBuf.writeBytes(packetC2S.toByteArray());
        byteBuf.writeBytes(SharedConstants.PACKET_DELIMITER);
    }
}
