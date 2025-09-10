package org.minigamzreborn.bytelyplay.velocity.listeners;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;
import org.minigamzreborn.bytelyplay.velocity.Main;
import org.minigamzreborn.bytelyplay.velocity.utils.Messages;

import java.util.Objects;
import java.util.Optional;

public class HandleAllCommands {
    @Subscribe
    public void onCommandExecuteEvent(CommandExecuteEvent e) {
        // please make this some better system with like brigadier
        if (e.getCommand().equals("hub")) {
            CommandSource source = e.getCommandSource();
            if (source instanceof Player p) {
                Optional<RegisteredServer> hubServer = Main.getInstance().getRandomServerOfType(ServerTypeOuterClass.ServerType.HUB);
                if (hubServer.isEmpty()) {
                    p.sendMessage(Messages.NO_SERVER_FOUND_TO_TRANSFER);
                    e.setResult(CommandExecuteEvent.CommandResult.allowed());
                    return;
                }
                p.createConnectionRequest(hubServer.orElseThrow()).fireAndForget();

                e.setResult(CommandExecuteEvent.CommandResult.denied());
                return;
            }
        }
        e.setResult(CommandExecuteEvent.CommandResult.forwardToServer());
    }
}
