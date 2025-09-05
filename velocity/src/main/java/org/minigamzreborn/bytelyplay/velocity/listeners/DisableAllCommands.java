package org.minigamzreborn.bytelyplay.velocity.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.CommandExecuteEvent;

public class DisableAllCommands {
    @Subscribe
    public void onCommandExecuteEvent(CommandExecuteEvent e) {
        e.setResult(CommandExecuteEvent.CommandResult.forwardToServer());
    }
}
