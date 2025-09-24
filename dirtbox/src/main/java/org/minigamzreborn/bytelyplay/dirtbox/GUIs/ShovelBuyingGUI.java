package org.minigamzreborn.bytelyplay.dirtbox.GUIs;

import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryClickEvent;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.event.inventory.InventoryOpenEvent;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.inventory.PlayerInventory;
import net.minestom.server.inventory.click.Click;
import net.minestom.server.item.ItemStack;
import org.minigamzreborn.bytelyplay.dirtbox.utils.CoinItemStacks;
import org.minigamzreborn.bytelyplay.dirtbox.utils.Messages;
import org.minigamzreborn.bytelyplay.dirtbox.utils.ShovelItemStacks;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class ShovelBuyingGUI extends Gui {
    public ShovelBuyingGUI() {
        super(InventoryType.CHEST_3_ROW, Component
                .text("Buy Shovels")
                .color(NamedTextColor.YELLOW));
        this.setItemStack(10, ShovelItemStacks.getShovel(1));
    }

    @Override
    public boolean preClick(Player p, Click click) {
        PlayerInventory inv = p.getInventory();
        ItemStack clicked = this.getItemStack(click.slot());
        if (clicked.isAir()) return true;

        int price = ShovelItemStacks.getShovelTier(clicked).orElse(-1);
        if (price == -1) return true;

        price *= 8;
        int coins = CoinItemStacks.getCoinsInList(Arrays.stream(inv.getItemStacks()).toList());
        if (coins >= price) {
            // TODO: Actually take the coins
            boolean added = p.getInventory().addItemStack(clicked);
            if (!added)
                p.sendMessage(Messages.NOT_ENOUGH_SPACE_IN_INVENTORY);
        } else {
            p.sendMessage(Messages.NOT_ENOUGH_COINS);
        }
        return true;
    }

    @Override
    public void clicked(Player p, Click click) {}
}
