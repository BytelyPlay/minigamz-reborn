package org.minigamzreborn.bytelyplay.protocol;

import org.minigamzreborn.bytelyplay.protocol.utils.Server;

public class ProtocolMain {
    public static void initServer(String ip, int port) {
        new ProtoServer().init(ip, port);
    }
    public static Server initClient(String ip, int port) {
        return new ProtoClient().init(ip, port);
    }
}
