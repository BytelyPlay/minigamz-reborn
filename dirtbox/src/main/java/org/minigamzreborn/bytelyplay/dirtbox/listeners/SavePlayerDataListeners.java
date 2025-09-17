package org.minigamzreborn.bytelyplay.dirtbox.listeners;

import lombok.extern.slf4j.Slf4j;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import org.minigamzreborn.bytelyplay.dirtbox.utils.SaveLoadPlayerData;

@Slf4j
public class SavePlayerDataListeners {
    private static void saveData(Player p) {
        SaveLoadPlayerData.savePlayerData(p);
    }
    public static void saveData(PlayerDisconnectEvent event) {
        saveData(event.getPlayer());
    }
}
