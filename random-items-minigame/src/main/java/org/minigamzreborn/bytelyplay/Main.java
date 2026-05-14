package org.minigamzreborn.bytelyplay;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.fabricmc.api.ModInitializer;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import org.minigamzreborn.bytelyplay.impl.ClientOperationsHandlerImpl;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.RegisterServerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protocol.ProtocolMain;
import org.minigamzreborn.bytelyplay.protocol.operationHandlers.client.ClientOperationsHandler;
import org.minigamzreborn.bytelyplay.protocol.utils.Server;

@Slf4j
public class Main implements ModInitializer {
	@Getter
	private static Main instance;
	@Getter
	private Server protocolServer;
	@Getter
	private static MinecraftServer minecraftServer;

	// TODO: Make configurable and actually use this field.
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
		ClientOperationsHandler.setInstance(new ClientOperationsHandlerImpl());
		protocolServer = ProtocolMain.initClient("127.0.0.1", 9485);
		protocolServer.sendPacket(
				WrappedPacketC2SOuterClass.WrappedPacketC2S.newBuilder()
						.setRegisterServerPacket(
								RegisterServerPacketC2SOuterClass.RegisterServerPacketC2S.newBuilder()
										// TODO: Make the address configurable
										.setAddress(mcServer.getLocalIp())
										.setPort(mcServer.getPort())
										.setType(ServerTypeOuterClass.ServerType.RANDOM_ITEMS)
										.build()
						)
						.build()
		);
	}
}
