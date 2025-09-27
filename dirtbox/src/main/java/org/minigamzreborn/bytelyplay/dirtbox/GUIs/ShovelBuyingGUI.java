package org.minigamzreborn.bytelyplay.dirtbox.GUIs;

import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.component.DataComponent;
import net.minestom.server.component.DataComponents;
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
import net.minestom.server.item.component.CustomData;
import org.minigamzreborn.bytelyplay.dirtbox.utils.CoinItemStacks;
import org.minigamzreborn.bytelyplay.dirtbox.utils.Messages;
import org.minigamzreborn.bytelyplay.dirtbox.utils.ShovelItemStacks;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class ShovelBuyingGUI extends Gui {
    public ShovelBuyingGUI() {
        super(InventoryType.CHEST_3_ROW, Component
                .text("Buy Shovels")
                .color(NamedTextColor.YELLOW));
        for (int i = 0; i < 7; i++) {
            int price = (i + 1) * (6 + i);
            ItemStack shovel = ShovelItemStacks.getShovel(i + 1);

            CustomData customData = Objects.requireNonNull(shovel.get(DataComponents.CUSTOM_DATA));
            CompoundBinaryTag tag = customData.nbt();

            List<Component> lore = shovel.get(DataComponents.LORE);
            if (lore == null) {
                lore = new ArrayList<>();
            } else {
                lore = new ArrayList<>(lore);
            }
            lore.add(Component.text(
                            "Price: " + price + " Coins"
                    ).color(NamedTextColor.GREEN)
                    .decoration(TextDecoration.ITALIC, false));

            this.setItemStack(i + 10, shovel
                    .withLore(lore)
                    .with(DataComponents.CUSTOM_DATA,
                            new CustomData(tag
                                    .putInt("price", price)))
            );
        }
    }

    @Override
    public boolean preClick(Player p, Click click) {
        PlayerInventory inv = p.getInventory();
        ItemStack clicked = this.getItemStack(click.slot());
        if (clicked.isAir()) return true;

        int tier = ShovelItemStacks.getShovelTier(clicked).orElse(-1);
        if (tier == -1) return true;

        int price = Objects.requireNonNull(clicked.get(DataComponents.CUSTOM_DATA)).nbt()
                .getInt("price");
        int coins = CoinItemStacks.getCoinsInList(Arrays.stream(inv.getItemStacks()).toList());
        log.debug("Click costs {} coins player has {} coins, player username is {}", price, coins, p.getName());
        if (coins >= price) {
            List<ItemStack> afterList = CoinItemStacks.takeCoinsFromList(
                    Arrays.stream(p.getInventory().getItemStacks()).toList(),
                    price
            );
            p.getInventory().clear();
            afterList.forEach(stack -> p.getInventory().addItemStack(stack));
            boolean added = p.getInventory().addItemStack(
                    ShovelItemStacks.getShovel(tier)
            );
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
