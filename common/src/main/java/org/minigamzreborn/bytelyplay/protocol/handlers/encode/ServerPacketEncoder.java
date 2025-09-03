package org.minigamzreborn.bytelyplay.protocol.handlers.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketS2COuterClass;

public class ServerPacketEncoder extends MessageToByteEncoder<WrappedPacketS2COuterClass.WrappedPacketS2C> {
    @Override
    protected void encode(ChannelHandlerContext ctx, WrappedPacketS2COuterClass.WrappedPacketS2C packet, ByteBuf byteBuf) {
        byte[] bytes = packet.toByteArray();
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}
