package org.minigamzreborn.bytelyplay.protocol;

public class ProtocolMain {
    public static void initServer() {
        new ProtoServer().init();
    }
    public static void initClient() {
        new ProtoClient().init();
    }
}
