package org.minigamzreborn.bytelyplay.dirtbox.utils;

import org.minigamzreborn.bytelyplay.dirtbox.Main;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.DisconnectPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.UnregisterServerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.WrappedPacketC2SOuterClass;

public class ShutdownLogic {
    public static void shutdownTasks() {
        Main.getInstance().getProtocolServer().sendPacket(
                WrappedPacketC2SOuterClass.WrappedPacketC2S.newBuilder()
                        .setUnregisterServerPacket(
                                UnregisterServerPacketC2SOuterClass.UnregisterServerPacketC2S.newBuilder()
                                        .setIp(Config.getInstance().getIp())
                                        .setPort(Config.getInstance().getPort())
                                        .build()
                        )
                        .build()
        );
        Main.getInstance().getProtocolServer().disconnect();
    }
}