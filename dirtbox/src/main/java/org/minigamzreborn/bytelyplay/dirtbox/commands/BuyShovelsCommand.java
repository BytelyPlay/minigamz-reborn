package org.minigamzreborn.bytelyplay.dirtbox.commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.entity.Player;
import org.minigamzreborn.bytelyplay.dirtbox.GUIs.ShovelBuyingGUI;

public class BuyShovelsCommand extends Command {

    public BuyShovelsCommand() {
        super("buyshovels");
        this.setDefaultExecutor(this::execute);
    }

    private void execute(CommandSender sender, CommandContext ctx) {
        if (sender instanceof Player p) {
            p.openInventory(new ShovelBuyingGUI());
        }
    }
}
