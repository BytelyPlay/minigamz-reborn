package org.minigamzreborn.bytelyplay.hub.NPCs;

import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import org.abstractvault.bytelyplay.data.DataSetter;
import org.abstractvault.bytelyplay.enums.DataFormat;
import org.jetbrains.annotations.NotNull;
import org.minigamzreborn.bytelyplay.hub.Main;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.TransferPlayerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.WrappedPacketC2SOuterClass;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
public class DirtBoxNPC extends NPC {
    private static PlayerSkin skin;
    private static final Path SKIN_CONFIG_PATH = Paths.get("./configuration/dirt_box_npc_skin.cbor");
    private static final DataSetter SKIN_SETTER = new DataSetter.Builder()
            .getterSetter(DirtBoxNPC::makeSkin, DirtBoxNPC::setSkin)
            .build();

    static {
        if (Files.exists(SKIN_CONFIG_PATH)) {
            SKIN_SETTER.load(SKIN_CONFIG_PATH);
        } else {
            SKIN_SETTER.save(SKIN_CONFIG_PATH, DataFormat.BINARY_CBOR);
        }
    }

    public DirtBoxNPC() {
        super(Component.text("DirtBox")
                .color(NamedTextColor.YELLOW)
                .decorate(TextDecoration.BOLD), skin);
    }

    @Override
    public void entityAttack(EntityAttackEvent event) {
        Entity attacker = event.getEntity();
        if (attacker instanceof Player p)
            Main.getInstance().getServer().sendPlayerToServer(p.getUuid(), ServerTypeOuterClass.ServerType.DIRTBOX);
    }

    @Override
    public void playerInteract(PlayerEntityInteractEvent event) {
        Player p = event.getPlayer();

        Main.getInstance().getServer().sendPlayerToServer(p.getUuid(), ServerTypeOuterClass.ServerType.DIRTBOX);
    }

    private static void setSkin(PlayerSkin playerSkin) {
        skin = playerSkin;
    }

    private static PlayerSkin makeSkin() {
        PlayerSkin pSkin = PlayerSkin.fromUuid("bcbaabb3-f21a-4927-94ad-2979c54f67fc");
        skin = pSkin;

        return pSkin;
    }
}
