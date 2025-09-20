package org.minigamzreborn.bytelyplay.dirtbox.GUIs;

import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.inventory.InventoryClickEvent;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.event.inventory.InventoryOpenEvent;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.event.trait.InventoryEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.inventory.click.Click;
import net.minestom.server.inventory.click.ClickType;
import org.jetbrains.annotations.NotNull;

@Slf4j
public abstract class Gui extends Inventory {
    public Gui(InventoryType inventoryType, Component title) {
        super(inventoryType, title);
    }

    // true if cancelled false if not
    public abstract boolean preClick(Player p, Click click);

    public abstract void clicked(Player p, Click click);

    @Override
    public boolean handleClick(@NotNull Player p, @NotNull Click click) {
        if (preClick(p, click)) {
            update();
            return false;
        }
        boolean result = super.handleClick(p, click);
        clicked(p, click);

        return result;
    }
}
