package org.minigamzreborn.bytelyplay.dirtbox.listeners;

import com.google.protobuf.Message;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.inventory.TransactionOption;
import net.minestom.server.item.ItemStack;
import org.minigamzreborn.bytelyplay.dirtbox.utils.CoinItemStacks;
import org.minigamzreborn.bytelyplay.dirtbox.utils.Messages;
import org.minigamzreborn.bytelyplay.dirtbox.utils.ShovelItemStacks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PlayerBlockBreakHandler {
    private static PlayerBlockBreakHandler instance;
    public static PlayerBlockBreakHandler getInstance() {
        return Objects.requireNonNullElseGet(instance, PlayerBlockBreakHandler::new);
    }
    private PlayerBlockBreakHandler() {
        if (instance != null) throw new IllegalStateException("Tried to create 2 instances of a singleton");
        instance = this;
    }

    public void blockBrokenDirtbox(PlayerBlockBreakEvent event) {
        if (event.getBlock() == Block.DIRT) {
            Player p = event.getPlayer();
            ItemStack stack = p.getItemInMainHand();

            Optional<Integer> shovelTier = ShovelItemStacks.getShovelTier(stack);
            if (shovelTier.isEmpty()) {
                List<ItemStack> notAddedRaw = p.getInventory().addItemStacks(CoinItemStacks.getCoins(1), TransactionOption.ALL);
                List<ItemStack> notAdded = removeUselessItemStacks(notAddedRaw);

                if (!notAdded.isEmpty()) p.sendMessage(Messages.NOT_ENOUGH_SPACE_IN_INVENTORY);
            } else {
                int tier = shovelTier.orElseThrow();
                List<ItemStack> notAddedRaw = p.getInventory().addItemStacks(CoinItemStacks.getCoins(tier  * 2), TransactionOption.ALL);
                List<ItemStack> notAdded = removeUselessItemStacks(notAddedRaw);

                if (!notAdded.isEmpty()) p.sendMessage(Messages.NOT_ENOUGH_SPACE_IN_INVENTORY);
            }
        } else {
            event.setCancelled(true);
        }
    }
    private List<ItemStack> removeUselessItemStacks(List<ItemStack> stacks) {
        ArrayList<ItemStack> updatedStacks = new ArrayList<>(stacks);

        updatedStacks.removeIf(stack -> stack.isAir() || stack.amount() <= 0);

        return List.copyOf(updatedStacks);
    }
}
