package org.minigamzreborn.bytelyplay.dirtbox.GUIs;

import lombok.extern.slf4j.Slf4j;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.inventory.InventoryClickEvent;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.event.inventory.InventoryOpenEvent;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.event.trait.InventoryEvent;
import net.minestom.server.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

@Slf4j
public abstract class Gui extends Inventory {
    /*
    * You Should NOT change the return value of getInventory whenever you'd like, make it a stable Inventory object, changing values inside of it is fine.
     */

    public abstract Inventory getInventory();

    public abstract void preClick(InventoryPreClickEvent e);
    public abstract void click(InventoryClickEvent e);
    public abstract void closed(InventoryCloseEvent e);
    public abstract void opened(InventoryOpenEvent e);

    private void inventoryEvent(InventoryEvent e) {
        log.debug("aikrjq3u9h delnemdelme delme");
        switch (e) {
            case InventoryPreClickEvent event -> preClick(event);
            case InventoryClickEvent event -> click(event);
            case InventoryCloseEvent event -> closed(event);
            case InventoryOpenEvent event -> opened(event);
            case null, default ->
                    log.debug("inventoryEvent function called with an InventoryEvent that isn't checked.");
        }
    }
    // When inventory is ready and everything.
    protected final void setup() {
        EventListener<@NotNull InventoryEvent> listener = EventListener.builder(InventoryEvent.class)
                .handler(this::inventoryEvent)
                .build();
        getInventory().eventNode().addListener(listener);
    }
}
