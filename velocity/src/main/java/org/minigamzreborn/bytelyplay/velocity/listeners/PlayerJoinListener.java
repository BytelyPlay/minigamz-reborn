package org.minigamzreborn.bytelyplay.velocity.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import lombok.extern.slf4j.Slf4j;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;
import org.minigamzreborn.bytelyplay.velocity.utils.ServerTypeRegistry;

import java.util.Optional;

@Slf4j
public class PlayerJoinListener {
    @Subscribe
    public void postLoginEvent(PlayerChooseInitialServerEvent event) {
        Optional<RegisteredServer> server = ServerTypeRegistry.getInstance().getRandomServerOfType(ServerTypeOuterClass.ServerType.HUB);
        if (server.isEmpty()) {
            log.warn("Couldn't send the player to a server because there are no servers to send him to.");
            return;
        }
        event.setInitialServer(server.orElseThrow());
    }
}
