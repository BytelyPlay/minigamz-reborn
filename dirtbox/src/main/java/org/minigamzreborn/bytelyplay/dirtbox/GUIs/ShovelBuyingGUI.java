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
import net.minestom.server.inventory.click.Click;
import net.minestom.server.item.ItemStack;
import org.minigamzreborn.bytelyplay.dirtbox.utils.Messages;
import org.minigamzreborn.bytelyplay.dirtbox.utils.ShovelItemStacks;

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
        ItemStack clicked = this.getItemStack(click.slot());
        if (clicked.isAir()) return true;

        boolean added = p.getInventory().addItemStack(clicked);
        if (!added)
            p.sendMessage(Messages.NOT_ENOUGH_SPACE_IN_INVENTORY);
        return true;
    }

    @Override
    public void clicked(Player p, Click click) {}
}
