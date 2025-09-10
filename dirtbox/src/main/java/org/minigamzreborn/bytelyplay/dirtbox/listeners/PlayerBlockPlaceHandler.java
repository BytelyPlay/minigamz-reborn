package org.minigamzreborn.bytelyplay.dirtbox.listeners;

import net.minestom.server.event.player.PlayerBlockPlaceEvent;

import java.util.Objects;

public class PlayerBlockPlaceHandler {
    private static PlayerBlockPlaceHandler instance;
    public static PlayerBlockPlaceHandler getInstance() {
        return Objects.requireNonNullElseGet(instance, PlayerBlockPlaceHandler::new);
    }
    private PlayerBlockPlaceHandler() {
        if (instance != null) throw new IllegalStateException("Tried to create 2 instances of a singleton");
        instance = this;
    }

    public static void blockPlace(PlayerBlockPlaceEvent event) {
        event.setCancelled(true);
    }
}
