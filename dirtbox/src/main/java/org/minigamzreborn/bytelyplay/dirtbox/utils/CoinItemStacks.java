package org.minigamzreborn.bytelyplay.dirtbox.utils;

import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.component.DataComponents;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.CustomData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CoinItemStacks {
    private static final ItemStack SINGLE_COIN_ITEMSTACK = ItemStack.of(Material.SUNFLOWER)
            .withCustomName(
                    Component.text("Coin")
                            .color(NamedTextColor.YELLOW)
                            .decorate(TextDecoration.BOLD)
                            .decoration(TextDecoration.ITALIC, false)
            );
    private static final String COMPRESSED_COIN_BLOCK_COMPRESSION_AMOUNT_KEY = "compression_amount";

    public static List<ItemStack> getCoins(int amount) {
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        if (amount < 9) {
            itemStacks.addAll(addItemStackToListMultiple(SINGLE_COIN_ITEMSTACK, amount));
        } else {
            int singleCoins = amount % 9;
            int trackingAmount = amount;

            itemStacks.addAll(addItemStackToListMultiple(SINGLE_COIN_ITEMSTACK, singleCoins));
            trackingAmount -= singleCoins;

            int compressionAmount = 0;
            while (trackingAmount > 9) {
                trackingAmount = trackingAmount / 9;
                compressionAmount++;
            }
            int singleAfterCoins = trackingAmount % 9;
            itemStacks.addAll(addItemStackToListMultiple(SINGLE_COIN_ITEMSTACK, singleAfterCoins));
            trackingAmount -= singleAfterCoins;

            if (trackingAmount <= 0) return itemStacks;

            itemStacks.addAll(addItemStackToListMultiple(getCompressedCoinBlock(compressionAmount), trackingAmount));
        }
        return itemStacks;
    }
    private static List<ItemStack> addItemStackToListMultiple(ItemStack stack, int amount) {
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            itemStacks.add(stack);
        }
        return itemStacks;
    }
    public static ItemStack getCompressedCoinBlock(int times) {
        return ItemStack.of(Material.GOLD_BLOCK)
                .withCustomName(
                        Component.text(times + "x ")
                                .color(NamedTextColor.YELLOW)
                                .decorate(TextDecoration.BOLD)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(
                                        Component.text("Compressed Coin Block")
                                                .color(NamedTextColor.GREEN)
                                                .decoration(TextDecoration.ITALIC, false)
                                )
                )
                .with(DataComponents.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder()
                        .putInt(COMPRESSED_COIN_BLOCK_COMPRESSION_AMOUNT_KEY, times)
                        .build()));
    }
    // Might not be there if COMPRESSED_COIN_BLOCK_COMPRESSION_AMOUNT_KEY isn't set.
    public static Optional<Integer> getCompressionAmount(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data == null) return Optional.empty();

        CompoundBinaryTag nbt = data.nbt();
        int compressionAmount = nbt.getInt(COMPRESSED_COIN_BLOCK_COMPRESSION_AMOUNT_KEY, -1);

        if (compressionAmount == -1) return Optional.empty();
        return Optional.of(compressionAmount);
    }
}
