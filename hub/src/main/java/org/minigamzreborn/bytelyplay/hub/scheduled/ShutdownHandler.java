package org.minigamzreborn.bytelyplay.hub.scheduled;

import org.minigamzreborn.bytelyplay.hub.Main;
import org.minigamzreborn.bytelyplay.hub.utils.Config;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.UnregisterServerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protocol.utils.Server;

public final class ShutdownHandler {
    public static void shutdown() {
        Main.getInstance().getServer().sendPacket(WrappedPacketC2SOuterClass.WrappedPacketC2S.newBuilder()
                .setUnregisterServerPacket(UnregisterServerPacketC2SOuterClass.UnregisterServerPacketC2S.newBuilder()
                        .setIp(Main.getInstance().getIpToRegisterWith())
                        .setPort(Config.getInstance().getPort())
                        .build())
                .build());
    }
}