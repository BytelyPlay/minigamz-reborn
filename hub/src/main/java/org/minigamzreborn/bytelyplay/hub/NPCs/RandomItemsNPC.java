package org.minigamzreborn.bytelyplay.hub.NPCs;

import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import org.abstractvault.bytelyplay.data.DataSetter;
import org.abstractvault.bytelyplay.enums.DataFormat;
import org.minigamzreborn.bytelyplay.hub.Main;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.TransferPlayerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.WrappedPacketC2SOuterClass;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class RandomItemsNPC extends NPC {
    private static final DataSetter SKIN_SETTER = new DataSetter.Builder()
            .getterSetter(RandomItemsNPC::getSkin, RandomItemsNPC::setSkin)
            .build();
    private static final Path SKIN_CONFIG_PATH = Paths.get("./configuration/random_items_npc_skin.cbor");
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
        super(USERNAME, skin);
    }

    @Override
    public void entityAttack(EntityAttackEvent event) {
        Entity attacker = event.getEntity();

        if (attacker instanceof Player p) {
            Main.getInstance().getServer().sendPlayerToServer(p.getUuid(),
                    ServerTypeOuterClass.ServerType.RANDOM_ITEMS);
        }
    }

    @Override
    public void playerInteract(PlayerEntityInteractEvent event) {
        Main.getInstance().getServer().sendPlayerToServer(event.getPlayer().getUuid(),
                ServerTypeOuterClass.ServerType.RANDOM_ITEMS);
    }

    public static PlayerSkin getSkin() {
        return PlayerSkin.fromUuid("8667ba71-b85a-4004-af54-457a9734eed7");
    }
}