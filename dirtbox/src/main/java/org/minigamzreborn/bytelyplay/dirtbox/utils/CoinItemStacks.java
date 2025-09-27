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
    private static final String COIN_WORTH = "coin_worth";
    private static final ItemStack SINGLE_COIN_ITEMSTACK = ItemStack.of(Material.SUNFLOWER)
            .withCustomName(
                    Component.text("Coin")
                            .color(NamedTextColor.YELLOW)
                            .decorate(TextDecoration.BOLD)
                            .decoration(TextDecoration.ITALIC, false)
            ).with(DataComponents.CUSTOM_DATA, new CustomData(CompoundBinaryTag
                    .builder()
                    .putInt(COIN_WORTH, 1)
                    .build()));

    // TODO: Make this work properly
    public static List<ItemStack> getCoins(int amount) {
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        int compressionLevel = 1;
        itemStacks.addAll(addItemStackToListMultiple(SINGLE_COIN_ITEMSTACK, amount % 9));
        amount /= 9;

        while (amount > 0) {
            int amountOfTier = amount % 9;
            itemStacks.addAll(
                    addItemStackToListMultiple(CoinItemStacks
                            .getCompressedCoinBlock(compressionLevel), amountOfTier)
            );
            amount /= 9;
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
        int coinWorth = (int) Math.pow(9, times);
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
                        .putInt(COIN_WORTH, coinWorth)
                        .build()));
    }
    // Might not be there if COMPRESSED_COIN_BLOCK_COMPRESSION_AMOUNT_KEY isn't set.
    public static Optional<Integer> getCoinWorth(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data == null) return Optional.empty();

        CompoundBinaryTag nbt = data.nbt();
        int compressionAmount = nbt.getInt(COIN_WORTH, -1) * stack.amount();

        if (compressionAmount == -1) return Optional.empty();
        return Optional.of(compressionAmount);
    }
    public static int getCoinsInList(List<ItemStack> stacks) {
        int amount = 0;
        for (ItemStack stack : stacks) {
            amount += getCoinWorth(stack).orElse(0);
        }
        return amount;
    }
    public static List<ItemStack> takeCoinsFromList(List<ItemStack> stacks, int amount) {
        List<ItemStack> modifiedStacks = new ArrayList<>();
        int left = amount;
        for (ItemStack stack : stacks) {
            int coinWorth = getCoinWorth(stack).orElse(0);

            if (coinWorth == 0) {
                modifiedStacks.add(stack);
                continue;
            }
            if (coinWorth == left) {
                modifiedStacks.remove(stack);
                modifiedStacks.add(stack.consume(left));
            }
            if (coinWorth > left) {
                // TODO
                modifiedStacks.add(stack);
            }
            left -= coinWorth;
        }
        return modifiedStacks;
    }
}
