package org.minigamzreborn.bytelyplay.protocol.utils;

import com.google.protobuf.GeneratedMessage;
import org.minigamzreborn.bytelyplay.protocol.operationHandlers.Server.CommonServerOperationsHandler;
import org.minigamzreborn.bytelyplay.protocol.operationHandlers.client.CommonClientOperationsHandler;
import org.minigamzreborn.bytelyplay.protocol.packetType.PacketTypeC2S;
import org.minigamzreborn.bytelyplay.protocol.packetType.PacketTypeS2C;
import org.minigamzreborn.bytelyplay.protocol.packetType.c2s.HandShakePacketTypeC2S;
import org.minigamzreborn.bytelyplay.protocol.packetType.c2s.TransferPlayerPacketTypeC2S;
import org.minigamzreborn.bytelyplay.protocol.packetType.c2s.UnregisterServerPacketTypeC2S;
import org.minigamzreborn.bytelyplay.protocol.packetType.s2c.HandShakePacketTypeS2C;
import org.minigamzreborn.bytelyplay.protocol.packetType.c2s.RegisterServerPacketTypeC2S;

import java.util.HashSet;

public class Packets {
    private static HashSet<PacketTypeC2S<?>> C2SPackets = new HashSet<>();
    private static HashSet<PacketTypeS2C<?>> S2CPackets = new HashSet<>();

    // Register all packet types:
    public static final HandShakePacketTypeC2S handShakePacketTypeC2S = registerC2S(
            new HandShakePacketTypeC2S(
                    CommonServerOperationsHandler::handShake
            )
    );
    public static final HandShakePacketTypeS2C handShakePacketTypeS2C = registerS2C(
            new HandShakePacketTypeS2C(
                    CommonClientOperationsHandler::handShaked
            )
    );
    public static final RegisterServerPacketTypeC2S registerServerPacketTypeC2S = registerC2S(
            new RegisterServerPacketTypeC2S(
                    CommonServerOperationsHandler::registerServer
            )
    );
    public static final UnregisterServerPacketTypeC2S unregisterServerPacketTypeC2S = registerC2S(
            new UnregisterServerPacketTypeC2S(
                    CommonServerOperationsHandler::unregisterServer
            )
    );
    public static final TransferPlayerPacketTypeC2S transferPlayerPacketTypeC2S = registerC2S(
      new TransferPlayerPacketTypeC2S(
              CommonServerOperationsHandler::transferPlayer
      )
    );

    public static HashSet<PacketTypeC2S<?>> getC2SPackets() {
        return (HashSet<PacketTypeC2S<?>>) C2SPackets.clone();
    }
    public static HashSet<PacketTypeS2C<?>> getS2CPackets() {
        return (HashSet<PacketTypeS2C<?>>) S2CPackets.clone();
    }
    public static <M extends GeneratedMessage, T extends PacketTypeS2C<M>> T registerS2C(T register) {
        S2CPackets.add(register);
        return register;
    }
    public static <M extends GeneratedMessage, T extends PacketTypeC2S<M>> T registerC2S(T register) {
        C2SPackets.add(register);
        return register;
    }
}
