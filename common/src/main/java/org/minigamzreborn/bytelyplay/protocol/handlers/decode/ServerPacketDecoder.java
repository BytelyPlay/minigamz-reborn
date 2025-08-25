package org.minigamzreborn.bytelyplay.protocol.handlers.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;

import java.util.List;

public class ServerPacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(WrappedPacketC2SOuterClass.WrappedPacketC2S.parseFrom(byteBuf.array()));
    }
}
