package org.minigamzreborn.bytelyplay.hub.NPCs;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import org.abstractvault.bytelyplay.data.DataSetter;
import org.abstractvault.bytelyplay.enums.DataFormat;
import org.jetbrains.annotations.NotNull;
import org.minigamzreborn.bytelyplay.hub.Main;
import org.minigamzreborn.bytelyplay.protobuffer.packets.TransferPlayerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class RandomItemsNPC extends NPC {
    private static final DataSetter SKIN_SETTER = new DataSetter.Builder()
            .getterSetter(RandomItemsNPC::getSkin, RandomItemsNPC::setSkin)
            .build();
    private static final Path SKIN_CONFIG_PATH = Paths.get("./configuration/skins.json");
    private static final Component USERNAME = Component.text("Random Items").style(
            style -> style
                    .color(TextColor.color(0, 255, 0))
                    .decorate(TextDecoration.BOLD)
    );
    static {
        if (Files.exists(SKIN_CONFIG_PATH)) {
            SKIN_SETTER.load(SKIN_CONFIG_PATH);
        } else {
            SKIN_SETTER.save(SKIN_CONFIG_PATH, DataFormat.BINARY_CBOR);
        }
    }
    @Setter
    private static PlayerSkin skin;

    public RandomItemsNPC() {
        super(GsonComponentSerializer.gson().serialize(USERNAME), skin);
    }

    @Override
    public void entityAttack(EntityAttackEvent event) {
        Entity attacker = event.getEntity();

        if (attacker instanceof Player p) {
            sendPlayerToRandomItems(p);
        }
    }

    @Override
    public void playerInteract(PlayerEntityInteractEvent event) {
        sendPlayerToRandomItems(event.getPlayer());
    }

    public static PlayerSkin getSkin() {
        return PlayerSkin.fromUuid("8667ba71-b85a-4004-af54-457a9734eed7");
    }
    private static void sendPlayerToRandomItems(Player p) {
        Main.getInstance().getServer().sendPacket(WrappedPacketC2SOuterClass.WrappedPacketC2S.newBuilder()
                .setTransferPlayerPacket(TransferPlayerPacketC2SOuterClass.TransferPlayerPacketC2S.newBuilder()
                        .setUuid(p.getUuid().toString())
                        .build())
                .build());
    }
}
