package org.minigamzreborn.bytelyplay.velocity.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class Messages {
    public static final Component NO_SERVER_FOUND_TO_TRANSFER = Component
            .text("We couldn't find a server of that type to connect you to.").style(
                    style -> style
                            .color(TextColor.color(255, 0, 0))
            );
}
