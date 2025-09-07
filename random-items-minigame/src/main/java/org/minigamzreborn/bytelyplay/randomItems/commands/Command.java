package org.minigamzreborn.bytelyplay.randomItems.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;

public abstract class Command {
    public abstract String getName();
    public abstract int execute(CommandContext<ServerCommandSource> context);
    public abstract LiteralArgumentBuilder<ServerCommandSource> changeArguments(LiteralArgumentBuilder<ServerCommandSource> arguments);
}
