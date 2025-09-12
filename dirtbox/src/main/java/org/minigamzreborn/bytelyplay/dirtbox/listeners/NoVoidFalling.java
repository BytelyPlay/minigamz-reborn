package org.minigamzreborn.bytelyplay.dirtbox.listeners;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerMoveEvent;
import org.minigamzreborn.bytelyplay.dirtbox.utils.Config;

import java.util.Objects;

public class NoVoidFalling {
    private static NoVoidFalling instance;
    public static NoVoidFalling getInstance() {
        return Objects.requireNonNullElseGet(instance, NoVoidFalling::new);
    }
    private NoVoidFalling() {
        if (instance != null) throw new IllegalStateException("Tried to create 2 instances of a singleton");
        instance = this;
    }

    public static void playerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();

        Pos oldPos = p.getPosition();
        Pos newPos = event.getNewPosition();

        if (oldPos.samePoint(newPos)) return;
        if (newPos.y() <= -50) {
            event.setCancelled(true);
            p.teleport(Config.getInstance().getSpawnPoint());
        }
    }
}
