package org.minigamzreborn.bytelyplay.dirtbox.listeners;

import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import org.minigamzreborn.bytelyplay.dirtbox.utils.SavePlayerInventory;

public class SaveLoadPlayerData {
    private static void saveData(Player p) {
        SavePlayerInventory.savePlayerInventory(p);
    }
    public static void saveData(PlayerDisconnectEvent event) {
        saveData(event.getPlayer());
    }
}
