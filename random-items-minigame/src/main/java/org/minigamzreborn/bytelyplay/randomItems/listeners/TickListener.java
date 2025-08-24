package org.minigamzreborn.bytelyplay.randomItems.listeners;

import net.minecraft.server.MinecraftServer;

public class TickListener {
    public static void tick(MinecraftServer server) {
        if (server.getTicks() % 201 == 200) {
            RandomItem.giveRandomItem(server);
        }
    }
}
