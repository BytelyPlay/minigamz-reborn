package org.minigamzreborn.bytelyplay.hub.events;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.player.PlayerSkinInitEvent;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.UUID;

public class PlayerSkinEvent {
    private static LoadingCache<@NotNull UUID, PlayerSkin> skinCache = Caffeine.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(Duration.ofDays(2))
            .build(uuid ->
                    PlayerSkin.fromUuid(String.valueOf(uuid)));
    public static void skinInitEvent(PlayerSkinInitEvent event) {
        event.setSkin(skinCache.get(event.getPlayer().getUuid()));
    }
}
