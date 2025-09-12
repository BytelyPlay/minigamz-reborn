package org.minigamzreborn.bytelyplay.dirtbox.listeners;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import org.minigamzreborn.bytelyplay.dirtbox.utils.Config;
import org.minigamzreborn.bytelyplay.dirtbox.utils.Instances;

import java.util.Objects;

public class PlayerJoinHandlers {
    private static PlayerJoinHandlers instance;
    public static PlayerJoinHandlers getInstance() {
        return Objects.requireNonNullElseGet(instance, PlayerJoinHandlers::new);
    }
    private PlayerJoinHandlers() {
        if (instance != null) throw new IllegalStateException("Tried to create 2 instances of a singleton");
        instance = this;
    }
    public void asyncPlayerConfigEvent(AsyncPlayerConfigurationEvent event) {
        event.setSpawningInstance(Instances.dirtbox);
        event.getPlayer().setRespawnPoint(Config.getInstance().getSpawnPoint());
    }
}
