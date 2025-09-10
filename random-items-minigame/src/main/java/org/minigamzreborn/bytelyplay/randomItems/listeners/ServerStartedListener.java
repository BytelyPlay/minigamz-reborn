package org.minigamzreborn.bytelyplay.randomItems.listeners;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import org.minigamzreborn.bytelyplay.randomItems.Main;
import org.minigamzreborn.bytelyplay.randomItems.utils.Commands;

public class ServerStartedListener {
    public static void started(MinecraftServer server) {
        Main main = Main.getInstance();
        Main.setMinecraftServer(server);
        main.initializeProtocol(server);

        CommandDispatcher<ServerCommandSource> dispatcher = server.getCommandManager().getDispatcher();

        // Commands.registerCommands(dispatcher);
    }
}
