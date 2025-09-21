package org.minigamzreborn.bytelyplay.velocity.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.bytelyplay.brigadierHelpers.commands.Command;
import org.jetbrains.annotations.NotNull;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;
import org.minigamzreborn.bytelyplay.velocity.Main;
import org.minigamzreborn.bytelyplay.velocity.utils.Messages;

import java.util.Optional;

public class HubCommand implements Command<CommandSource> {
    @Override
    public @NotNull LiteralArgumentBuilder<CommandSource> build() {
        return LiteralArgumentBuilder.<CommandSource>literal("hub")
                .executes(this::execute);
    }

    private int execute(CommandContext<CommandSource> ctx) {
        CommandSource source = ctx.getSource();
        if (source instanceof Player p) {
            Optional<RegisteredServer> hubServer = Main.getInstance().getRandomServerOfType(ServerTypeOuterClass.ServerType.HUB);
            if (hubServer.isEmpty()) {
                p.sendMessage(Messages.NO_SERVER_FOUND_TO_TRANSFER);
                return 1;
            }
            p.createConnectionRequest(hubServer.orElseThrow()).fireAndForget();
            return 1;
        }
        return 1;
    }
}
