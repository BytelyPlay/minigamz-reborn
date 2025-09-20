package org.minigamzreborn.bytelyplay.dirtbox.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import org.minigamzreborn.bytelyplay.dirtbox.constants.Instances;
import org.minigamzreborn.bytelyplay.dirtbox.utils.Config;
import org.minigamzreborn.bytelyplay.dirtbox.utils.PlayerInventorySerializerDeserializer;
import org.minigamzreborn.bytelyplay.dirtbox.utils.SaveLoadPlayerData;

import java.util.Objects;
import java.util.Optional;

public class PlayerJoinHandlers {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static PlayerJoinHandlers instance;
    public static PlayerJoinHandlers getInstance() {
        return Objects.requireNonNullElseGet(instance, PlayerJoinHandlers::new);
    }
    private PlayerJoinHandlers() {
        if (instance != null) throw new IllegalStateException("Tried to create 2 instances of a singleton");
        instance = this;
    }
    public void asyncPlayerConfigEvent(AsyncPlayerConfigurationEvent event) {
        Player p = event.getPlayer();

        event.setSpawningInstance(Instances.dirtbox);
        p.setRespawnPoint(Config.getInstance().getSpawnPoint());

        Optional<JsonNode> playerData = SaveLoadPlayerData.getPlayerData(p);
        if (playerData.isPresent()) {
            PlayerInventorySerializerDeserializer.fillInventory(
                    playerData.orElseThrow(),
                    p.getInventory()
            );
        }
    }
}
