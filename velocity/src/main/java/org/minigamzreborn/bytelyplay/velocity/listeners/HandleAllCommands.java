package org.minigamzreborn.bytelyplay.velocity.listeners;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;
import org.minigamzreborn.bytelyplay.velocity.Main;
import org.minigamzreborn.bytelyplay.velocity.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class HandleAllCommands {
    private static final List<String> UNHANDLED_COMMANDS = List.of(new String[]{
            "shutdown"
    });
    @Subscribe
    public void onCommandExecuteEvent(CommandExecuteEvent e) {
        if (UNHANDLED_COMMANDS.contains(e.getCommand().toLowerCase().replace(" ", ""))) return;

        CommandDispatcher<CommandSource> dispatcher = Main.getInstance().getDispatcher();

        String commandNoArgs = Arrays.stream(e.getCommand().split(" ")).findFirst().orElse(null);
        if (commandNoArgs != null) {
            if (dispatcher.getRoot().getChild(commandNoArgs) != null) {
                try {
                    dispatcher.execute(e.getCommand(), e.getCommandSource());
                } catch (CommandSyntaxException ex) {
                    e.getCommandSource().sendMessage(Component.text(ex.getMessage())
                            .color(NamedTextColor.RED));
                }
                e.setResult(CommandExecuteEvent.CommandResult.denied());
            }
        } else {
            log.warn("Bug? commandNoArgs is null.");
        }
        e.setResult(CommandExecuteEvent.CommandResult.forwardToServer());
    }
}
