package org.minigamzreborn.bytelyplay.protocol;

import org.minigamzreborn.bytelyplay.protocol.utils.Server;

public class ProtocolMain {
    public static ProtoServer initServer(String ip, int port) {
        ProtoServer server = new ProtoServer();
        server.init(ip, port);
        return server;
    }
    public static Server initClient(String ip, int port) {
        return new ProtoClient().init(ip, port);
    }
}
