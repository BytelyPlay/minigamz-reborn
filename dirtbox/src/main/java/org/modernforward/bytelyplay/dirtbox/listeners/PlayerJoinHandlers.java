package org.modernforward.bytelyplay.dirtbox.listeners;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import org.modernforward.bytelyplay.dirtbox.utils.Instances;

public class PlayerJoinHandlers {
    public static void asyncPlayerConfigEvent(AsyncPlayerConfigurationEvent event) {
        event.setSpawningInstance(Instances.dirtbox);
        // TODO: Make this a json configuration option.
        event.getPlayer().setRespawnPoint(new Pos(0, 10, 0, 90f, 0f));
    }
}
