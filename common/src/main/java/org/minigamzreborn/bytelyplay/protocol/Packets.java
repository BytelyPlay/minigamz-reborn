package org.minigamzreborn.bytelyplay.protocol;

import com.google.protobuf.GeneratedMessage;
import org.minigamzreborn.bytelyplay.protocol.packetType.PacketType;
import org.minigamzreborn.bytelyplay.protocol.packetType.PacketTypeC2S;
import org.minigamzreborn.bytelyplay.protocol.packetType.PacketTypeS2C;

import java.util.HashSet;

public class Packets {
    public static HashSet<PacketTypeC2S<?>> C2SPackets = new HashSet<>();
    public static HashSet<PacketTypeS2C<?>> S2CPackets = new HashSet<>();

    // Register all packet types:


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
