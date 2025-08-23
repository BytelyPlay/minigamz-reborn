package org.minigamzreborn.bytelyplay.hub.NPCs;

import net.minestom.server.entity.*;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import net.minestom.server.network.packet.server.play.PlayerInfoRemovePacket;
import net.minestom.server.network.packet.server.play.PlayerInfoUpdatePacket;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public abstract class NPC extends Entity {
    private final String username;
    private final PlayerSkin skin;

    public NPC(@NotNull String username, @NotNull PlayerSkin skin) {
        super(EntityType.PLAYER);

        this.username = username;
        this.skin = skin;
    }

    @Override
    public void updateNewViewer(@NotNull Player p) {
        ArrayList<PlayerInfoUpdatePacket.Property> properties = new ArrayList<>();
        properties.add(new PlayerInfoUpdatePacket.Property("textures", skin.textures(), skin.signature()));

        PlayerInfoUpdatePacket.Entry entry = new PlayerInfoUpdatePacket.Entry(this.getUuid(), this.username,
                properties, false, -1,
                GameMode.SURVIVAL, null, null, 0);

        p.sendPacket(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.ADD_PLAYER, entry));

        super.updateNewViewer(p);
    }

    @Override
    public void updateOldViewer(@NotNull Player p) {
        super.updateOldViewer(p);

        p.sendPacket(new PlayerInfoRemovePacket(this.getUuid()));
    }

    public abstract void entityAttack(EntityAttackEvent event);
    public abstract void playerInteract(PlayerEntityInteractEvent event);
}
