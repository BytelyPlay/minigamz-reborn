package org.minigamzreborn.bytelyplay.hub.NPCs;

import net.kyori.adventure.text.Component;
import net.minestom.server.component.DataComponents;
import net.minestom.server.entity.*;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import net.minestom.server.network.packet.server.play.PlayerInfoRemovePacket;
import net.minestom.server.network.packet.server.play.PlayerInfoUpdatePacket;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class NPC extends Entity {
    private final Component displayName;
    private final String username;
    private final PlayerSkin skin;

    public NPC(@NotNull Component displayName, @NotNull PlayerSkin skin) {
        super(EntityType.PLAYER);

        this.displayName = displayName;
        this.skin = skin;

        ThreadLocalRandom random = ThreadLocalRandom.current();

        byte[] randomBytes = new byte[8];
        random.nextBytes(randomBytes);

        StringBuilder hex = new StringBuilder();

        for (byte b : randomBytes) {
            hex.append(String.format("%02X", b));
        }

        this.username = hex.toString();
    }

    @Override
    public void updateNewViewer(@NotNull Player p) {
        ArrayList<PlayerInfoUpdatePacket.Property> properties = new ArrayList<>();
        properties.add(new PlayerInfoUpdatePacket.Property("textures", skin.textures(), skin.signature()));

        PlayerInfoUpdatePacket.Entry entry = new PlayerInfoUpdatePacket.Entry(this.getUuid(), this.username,
                properties, false, -1,
                GameMode.SURVIVAL, displayName, null, 0);

        p.sendPacket(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.ADD_PLAYER, entry));

        super.updateNewViewer(p);
    }

    @Override
    public void updateOldViewer(@NotNull Player p) {
        super.updateOldViewer(p);

        p.sendPacket(new PlayerInfoRemovePacket(this.getUuid()));
    }

    @Override
    public void spawn() {
        this.set(DataComponents.CUSTOM_NAME, displayName);
        this.setCustomNameVisible(true);
    }

    public abstract void entityAttack(EntityAttackEvent event);
    public abstract void playerInteract(PlayerEntityInteractEvent event);
}
