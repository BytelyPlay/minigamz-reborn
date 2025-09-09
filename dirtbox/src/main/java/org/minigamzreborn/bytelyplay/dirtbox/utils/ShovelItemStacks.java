package org.minigamzreborn.bytelyplay.dirtbox.utils;

import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.component.DataComponent;
import net.minestom.server.component.DataComponents;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.CustomData;
import net.minestom.server.recipe.display.SlotDisplay;

import java.util.Optional;

public class ShovelItemStacks {
    private static final String SHOVEL_TIER_KEY = "shovel_tier";

    public static ItemStack getShovel(int tier) {
        if (tier <= 0) throw new IllegalArgumentException("tier cannot be less than 1");
        return ItemStack.of(Material.AIR).withCustomName(
                Component.text("Tier ")
                        .color(NamedTextColor.GREEN)
                        .append(Component.text(tier)
                                .color(NamedTextColor.DARK_RED))
                        .append(Component.text(" Shovel")
                                .color(NamedTextColor.AQUA))
        ).withLore(
                Component.text(
                        "This shovel doubles your coins, this tier gives you " + tier * 2 + " coins per block broken."
                )
        ).with(DataComponents.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder()
                .putInt(SHOVEL_TIER_KEY, tier)
                .build()));
    }
    /* Returns an Optional.empty() if it isn't a shovel, so like a fist or a coin */
    public static Optional<Integer> getShovelTier(ItemStack stack) {
        if (stack == ItemStack.AIR) return Optional.empty();

        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData == null) return Optional.empty();

        int value = customData.nbt().getInt(SHOVEL_TIER_KEY, -1);
        if (value == -1) return Optional.empty();

        return Optional.of(value);
    }
}
