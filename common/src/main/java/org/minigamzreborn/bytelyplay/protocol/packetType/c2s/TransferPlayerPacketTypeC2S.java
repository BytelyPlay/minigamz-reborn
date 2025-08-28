package org.minigamzreborn.bytelyplay.protocol.packetType.c2s;

import com.google.protobuf.Parser;
import org.minigamzreborn.bytelyplay.protobuffer.packets.TransferPlayerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protocol.packetType.PacketTypeC2S;
import org.minigamzreborn.bytelyplay.protocol.utils.Client;

import java.util.function.BiConsumer;

public class TransferPlayerPacketTypeC2S extends PacketTypeC2S<TransferPlayerPacketC2SOuterClass.TransferPlayerPacketC2S> {
    public TransferPlayerPacketTypeC2S(BiConsumer<TransferPlayerPacketC2SOuterClass.TransferPlayerPacketC2S,
            Client> handler) {
        super(TransferPlayerPacketC2SOuterClass.TransferPlayerPacketC2S.parser(),
                handler,
                TransferPlayerPacketC2SOuterClass.TransferPlayerPacketC2S.class);
    }

    @Override
    public boolean isWrappedPacketThis(WrappedPacketC2SOuterClass.WrappedPacketC2S packetS2C) {
        return packetS2C.hasTransferPlayerPacket();
    }

    @Override
    public void receivedPacketWrapped(WrappedPacketC2SOuterClass.WrappedPacketC2S packet, Client client) {
        receivedPacket(packet.getTransferPlayerPacket(), client);
    }
}
