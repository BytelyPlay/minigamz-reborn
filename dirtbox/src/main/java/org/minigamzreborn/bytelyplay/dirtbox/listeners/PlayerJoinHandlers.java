package org.minigamzreborn.bytelyplay.dirtbox.listeners;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import org.bson.Document;
import org.minigamzreborn.bytelyplay.dirtbox.constants.Instances;
import org.minigamzreborn.bytelyplay.dirtbox.utils.Config;
import org.minigamzreborn.bytelyplay.dirtbox.utils.PlayerInventorySerializerDeserializer;
import org.minigamzreborn.bytelyplay.dirtbox.utils.SaveLoadPlayerData;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.Objects;
import java.util.Optional;

@Slf4j
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

        Optional<Document> playerData = SaveLoadPlayerData.getPlayerData(p);

        if (playerData.isPresent()) {
            try {
                PlayerInventorySerializerDeserializer.fillInventory(
                        playerData.orElseThrow(),
                        p.getInventory()
                );
            } catch (IOException e) {
                log.warn("Couldn't fill inventory... IOException thrown...");
                p.kick("Sorry, something went wrong during configuration.");
            }
        }
    }
}
