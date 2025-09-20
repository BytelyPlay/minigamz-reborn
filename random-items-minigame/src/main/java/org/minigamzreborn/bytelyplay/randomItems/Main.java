package org.minigamzreborn.bytelyplay.randomItems;

import com.mojang.brigadier.CommandDispatcher;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.RegisterServerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.WrappedPacketC2SOuterClass;
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
    public static BlockPos SPAWN_POINT = new BlockPos(0, 3, 0);
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
                                        .setAddress(minecraftServer.getServerIp())
                                        .setPort(minecraftServer.getServerPort())
                                        .setType(ServerTypeOuterClass.ServerType.RANDOM_ITEMS)
                                        .build()
                        )
                        .build()
        );
    }
}
