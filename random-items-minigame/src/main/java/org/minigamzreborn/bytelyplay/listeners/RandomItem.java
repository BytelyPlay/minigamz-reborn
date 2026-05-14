package org.minigamzreborn.bytelyplay.listeners;

import lombok.extern.slf4j.Slf4j;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class RandomItem {
    public static void giveRandomItem(MinecraftServer server) {
        List<ServerPlayer> players = server.getPlayerList().getPlayers();

        for (ServerPlayer player : players) {
            ResourceKey<Registry<Item>> itemRegistryKey = Registries.ITEM;
            Optional<Holder.Reference<Registry<Item>>> optItemRegistry =
                    server.registryAccess().get(itemRegistryKey);
            Registry<Item> itemRegistry =
                    optItemRegistry.orElseThrow(() ->
                            new RuntimeException(
                                    "This should not be thrown, item registry cannot be found."
                            ))
                            .value();
            int itemID = ThreadLocalRandom.current().nextInt(0, itemRegistry.size());

            Optional<Holder.Reference<Item>> optRandomItem = itemRegistry.get(itemID);
            if (optRandomItem.isEmpty()) {
                player.sendSystemMessage(Component
                        .literal("Failed to give random item.")
                        .withStyle(ChatFormatting.RED), false);
                log.warn("Couldn't give random item to: {}", player.getName());
                return;
            }
            Item randomItem = optRandomItem.orElseThrow().value();
            ItemStack randomStack = new ItemStack(randomItem);

            if (!player.addItem(randomStack))
                player.drop(randomStack, true, false);
        }
    }
}
