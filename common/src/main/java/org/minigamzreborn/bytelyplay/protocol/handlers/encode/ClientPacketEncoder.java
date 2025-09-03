package org.minigamzreborn.bytelyplay.protocol.handlers.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;

public class ClientPacketEncoder extends MessageToByteEncoder<WrappedPacketC2SOuterClass.WrappedPacketC2S> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, WrappedPacketC2SOuterClass.WrappedPacketC2S packet, ByteBuf byteBuf) throws Exception {
        byte[] bytes = packet.toByteArray();
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}
