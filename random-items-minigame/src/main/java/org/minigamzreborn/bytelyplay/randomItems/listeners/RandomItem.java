package org.minigamzreborn.bytelyplay.randomItems.listeners;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomItem {
    public static void giveRandomItem(MinecraftServer server) {
        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
        for (ServerPlayerEntity player : players) {
            DefaultedRegistry<Item> itemRegistry = Registries.ITEM;
            int itemID = ThreadLocalRandom.current().nextInt(0, itemRegistry.size());

            Item randomItem = itemRegistry.get(itemID);
            player.giveOrDropStack(new ItemStack(randomItem));
        }
    }
}
