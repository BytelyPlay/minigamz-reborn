package org.minigamzreborn.bytelyplay.hub.NPCs;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.component.DataComponents;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.*;
import net.minestom.server.entity.metadata.EntityMeta;
import net.minestom.server.entity.metadata.display.AbstractDisplayMeta;
import net.minestom.server.entity.metadata.display.TextDisplayMeta;
import net.minestom.server.entity.metadata.other.ArmorStandMeta;
import net.minestom.server.entity.metadata.other.InteractionMeta;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.play.*;
import net.minestom.server.scoreboard.Team;
import net.minestom.server.scoreboard.TeamBuilder;
import net.minestom.server.scoreboard.TeamManager;
import net.minestom.server.timer.TaskSchedule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public abstract class NPC extends Entity {
    private final Component displayName;
    private final String username;
    private final PlayerSkin skin;

    private final Entity textDisplayNameTag;
    private final Entity interactionEntityAnchor;

    public NPC(@NotNull Component displayName, @NotNull PlayerSkin skin) {
        super(EntityType.PLAYER);

        this.displayName = displayName;
        this.skin = skin;

        this.username = this.getUuid().toString().substring(0, 16);

        this.textDisplayNameTag = new Entity(EntityType.TEXT_DISPLAY);
        this.textDisplayNameTag.editEntityMeta(TextDisplayMeta.class, metadata -> {
            metadata.setBillboardRenderConstraints(AbstractDisplayMeta.BillboardConstraints.CENTER);
            metadata.setText(displayName);
        });

        this.interactionEntityAnchor = new Entity(EntityType.INTERACTION);
        this.interactionEntityAnchor.editEntityMeta(InteractionMeta.class, metadata -> {
            metadata.setHeight(0.25f);
        });
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

        String teamName = "NPC-" + this.getUuid();
        TeamsPacket createTeamPacket = new TeamsPacket(teamName, new TeamsPacket.CreateTeamAction(
                Component.empty(),
                (byte) 0,
                TeamsPacket.NameTagVisibility.NEVER,
                TeamsPacket.CollisionRule.NEVER,
                NamedTextColor.GREEN,
                Component.empty(),
                Component.empty(),
                List.of()
        ));
        TeamsPacket addEntityToTeamPacket = new TeamsPacket(teamName, new TeamsPacket.AddEntitiesToTeamAction(List.of(this.username)));

        p.sendPackets(createTeamPacket, addEntityToTeamPacket);
    }

    @Override
    public void updateOldViewer(@NotNull Player p) {
        super.updateOldViewer(p);

        p.sendPacket(new PlayerInfoRemovePacket(this.getUuid()));
        p.sendPacket(new TeamsPacket("NPC-" + this.getUuid(), new TeamsPacket.RemoveTeamAction()));
    }

    @Override
    public void spawn() {
        interactionEntityAnchor.setInstance(this.getInstance());
        textDisplayNameTag.setInstance(this.getInstance());

        this.addPassenger(interactionEntityAnchor);

        interactionEntityAnchor.addPassenger(textDisplayNameTag);
    }

    @Override
    protected void despawn() {
        interactionEntityAnchor.remove();
        textDisplayNameTag.remove();
    }

    public abstract void entityAttack(EntityAttackEvent event);
    public abstract void playerInteract(PlayerEntityInteractEvent event);
}
