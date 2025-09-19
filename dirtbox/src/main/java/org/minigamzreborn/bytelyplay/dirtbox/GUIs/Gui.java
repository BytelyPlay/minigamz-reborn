package org.minigamzreborn.bytelyplay.dirtbox.GUIs;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.inventory.InventoryClickEvent;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.event.inventory.InventoryOpenEvent;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.event.trait.InventoryEvent;
import net.minestom.server.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.IOException;

public abstract class Gui {
    public Gui() {
        EventListener<@NotNull InventoryEvent> listener = EventListener.builder(InventoryEvent.class)
                .filter(e -> e.getInventory() == this.getInventory())
                .handler(this::inventoryEvent)
                .build();
        getInventory().eventNode().addListener(listener);
    }

    public abstract Inventory getInventory();

    public abstract void preClick(InventoryPreClickEvent e);
    public abstract void click(InventoryClickEvent e);
    public abstract void closed(InventoryCloseEvent e);
    public abstract void opened(InventoryOpenEvent e);

    private void inventoryEvent(InventoryEvent e) {
        if (e instanceof InventoryPreClickEvent event) {
            preClick(event);
        } else if (e instanceof InventoryClickEvent event) {
            click(event);
        } else if (e instanceof InventoryPreClickEvent event) {
            preClick(event);
        } else if (e instanceof InventoryCloseEvent event) {
             closed(event);
        } else if (e instanceof InventoryOpenEvent event) {
            opened(event);
        }
    }
}
