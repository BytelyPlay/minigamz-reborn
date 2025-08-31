package org.minigamzreborn.bytelyplay.randomItems;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.tick.SimpleTickScheduler;
import net.minecraft.world.tick.TickScheduler;
import org.minigamzreborn.bytelyplay.protobuffer.packets.RegisterServerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protocol.ProtocolMain;
import org.minigamzreborn.bytelyplay.protocol.utils.Server;

@Slf4j
public class Main implements ModInitializer {
    @Getter
    private static Main instance;
    @Getter
    private Server protocolServer;
    @Getter
    private static MinecraftServer minecraftServer;
    @Override
    public void onInitialize() {
        instance = this;
    }

    public static void setMinecraftServer(MinecraftServer mcServer) {
        if (minecraftServer != null) {
            log.warn("this.minecraftServer doesn't equal null but is being set? why is it being set twice? mod bugged?");
            return;
        }
        minecraftServer = mcServer;
    }
    public void initializeProtocol(MinecraftServer mcServer) {
        protocolServer = ProtocolMain.initClient("127.0.0.1", 9485);
        protocolServer.sendPacket(
                WrappedPacketC2SOuterClass.WrappedPacketC2S.newBuilder()
                        .setRegisterServerPacket(
                                RegisterServerPacketC2SOuterClass.RegisterServerPacketC2S.newBuilder()
                                        .setAddress("127.0.0.1")
                                        .setPort(minecraftServer.getServerPort())
                                        .build()
                        )
                        .build()
        );
    }
}
