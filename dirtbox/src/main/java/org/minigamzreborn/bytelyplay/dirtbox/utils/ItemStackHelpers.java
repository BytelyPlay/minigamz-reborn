package org.minigamzreborn.bytelyplay.dirtbox.utils;

import net.minestom.server.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemStackHelpers {
    public static List<ItemStack> removeUselessItemStacks(List<ItemStack> stacks) {
        ArrayList<ItemStack> updatedStacks = new ArrayList<>(stacks);

        updatedStacks.removeIf(stack -> stack.isAir() || stack.amount() <= 0);

        return List.copyOf(updatedStacks);
    }
}
