package org.minigamzreborn.bytelyplay.dirtbox.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;

public class Messages {
    public static final Component NOT_ENOUGH_SPACE_IN_INVENTORY = Component
            .text("There was not enough space in your inventory to give you the items.")
            .color(NamedTextColor.GREEN)
            .decorate(TextDecoration.BOLD);
}
