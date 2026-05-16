package org.minigamzreborn.bytelyplay.hub.npcs;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public final class RandomItemsNPC extends NPC {
    private static final DataSetter SKIN_SETTER = new DataSetter.Builder()
            .getterSetter(RandomItemsNPC::getSkin, RandomItemsNPC::setSkin)
            .build();
    private static final Path SKIN_CONFIG_PATH =
            Path.of("./")
            .resolve("config")
            .resolve("random-items-minigames-skin.cbor");
    private static final Component USERNAME = Component.text("Random Items").style(
            style -> style
                    .color(TextColor.color(0, 255, 0))
                    .decorate(TextDecoration.BOLD)
    );
    static {
        try {
            if (Files.exists(SKIN_CONFIG_PATH)) {
                SKIN_SETTER.load(SKIN_CONFIG_PATH);
            } else {
                SKIN_SETTER.save(SKIN_CONFIG_PATH, DataFormat.BINARY_CBOR);
            }
        } catch (IOException e) {
            log.warn("Couldn't save/load skin, probably not a big deal, " +
                    "it just means we have to request it from the Mojang API again.", e);
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
        if (skin == null)
            skin = PlayerSkin.fromUuid("8667ba71-b85a-4004-af54-457a9734eed7");
        return skin;
    }
}