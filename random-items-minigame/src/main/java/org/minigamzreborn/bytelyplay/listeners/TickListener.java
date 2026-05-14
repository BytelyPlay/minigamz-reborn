package org.minigamzreborn.bytelyplay.listeners;

import net.minecraft.server.MinecraftServer;

public class TickListener {
    public static void tick(MinecraftServer server) {
        if (server.getTickCount() % 200 == 0) {
            RandomItem.giveRandomItem(server);
        }
    }
}
