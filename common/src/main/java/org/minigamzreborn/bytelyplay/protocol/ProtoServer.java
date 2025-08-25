package org.minigamzreborn.bytelyplay.protocol;

import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import org.minigamzreborn.bytelyplay.protocol.Constants.SharedConstants;
import org.minigamzreborn.bytelyplay.protocol.handlers.encode.ServerPacketEncoder;
import org.minigamzreborn.bytelyplay.protocol.handlers.logic.ServerLogicHandler;
import org.minigamzreborn.bytelyplay.protocol.handlers.decode.ServerPacketDecoder;

import java.io.IOException;

public class ProtoServer {
    public void init() {
        try {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setupChannel(SocketChannel channel) {
        Client client = new Client(channel);
        channel.pipeline().addLast(
                new DelimiterBasedFrameDecoder(1024, SharedConstants.PACKET_DELIMITER),
                new ServerPacketDecoder(),
                new ServerLogicHandler(client),
                new ServerPacketEncoder()
        );
    }
}
