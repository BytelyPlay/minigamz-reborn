package org.minigamzreborn.bytelyplay.randomItems.utils;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.ServerCommandSource;
import org.minigamzreborn.bytelyplay.randomItems.commands.Command;

import java.util.ArrayList;

public class Commands {
    // this is for if dispatcher is null.
    private static ArrayList<Command> queue = new ArrayList<>();
    private static CommandDispatcher<ServerCommandSource> dispatcher;
    public static void register(Command cmd) {
        if (dispatcher == null) {
            queue.add(cmd);
            return;
        }
        registerCommand(dispatcher, cmd);
    }
    public static void registerCommands(CommandDispatcher<ServerCommandSource> cmdDispatcher) {
        dispatcher=cmdDispatcher;
        for (Command cmd : queue) {
            registerCommand(cmdDispatcher, cmd);
        }
    }
    private static void registerCommand(CommandDispatcher<ServerCommandSource> cmdDispatcher, Command cmd) {
        LiteralArgumentBuilder<ServerCommandSource> beforeArguments = LiteralArgumentBuilder.
                literal(cmd.getName());
        LiteralArgumentBuilder<ServerCommandSource> afterArguments = cmd.changeArguments(beforeArguments);
        if (afterArguments == null) {
            afterArguments = beforeArguments;
            afterArguments = afterArguments.executes(cmd::execute);
        }
        dispatcher.register(afterArguments);
    }
}
