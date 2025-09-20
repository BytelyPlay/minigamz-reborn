package org.minigamzreborn.bytelyplay.dirtbox.GUIs;

import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryClickEvent;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.event.inventory.InventoryOpenEvent;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import org.minigamzreborn.bytelyplay.dirtbox.utils.Messages;
import org.minigamzreborn.bytelyplay.dirtbox.utils.ShovelItemStacks;

@Slf4j
public class ShovelBuyingGUI extends Gui {
    private final Inventory inv = new Inventory(InventoryType.CHEST_3_ROW, Component
            .text("Buy Shovels")
            .color(NamedTextColor.YELLOW));

    public ShovelBuyingGUI() {
        inv.setItemStack(10, ShovelItemStacks.getShovel(1));
        setup();
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    @Override
    public void preClick(InventoryPreClickEvent e) {
        log.debug("delme");
        e.setCancelled(true);

        Player p = e.getPlayer();

        ItemStack clicked = e.getClickedItem();
        if (clicked.isAir()) return;

        boolean added = p.getInventory().addItemStack(clicked);
        if (!added)
            p.sendMessage(Messages.NOT_ENOUGH_SPACE_IN_INVENTORY);
    }

    @Override
    public void closed(InventoryCloseEvent e) {}
    @Override
    public void opened(InventoryOpenEvent e) {}
    @Override
    public void click(InventoryClickEvent e) {}
}
