package org.minigamzreborn.bytelyplay.hub.events;

import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import org.minigamzreborn.bytelyplay.hub.utils.Config;
import org.minigamzreborn.bytelyplay.hub.utils.Instances;

public class PlayerLoginHandler {
    public static void asyncPlayerConfigEvent(AsyncPlayerConfigurationEvent e) {
        e.setSpawningInstance(Instances.hub);
        e.getPlayer().setRespawnPoint(Config.getInstance().getSpawnPoint());
    }
}
