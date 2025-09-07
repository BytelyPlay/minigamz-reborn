package org.minigamzreborn.bytelyplay.randomItems.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.minigamzreborn.bytelyplay.protobuffer.enums.ServerTypeOuterClass;
import org.minigamzreborn.bytelyplay.randomItems.Main;

@Slf4j
public class HubCommand extends Command {
    @Override
    public String getName() {
        return "hub";
    }

    @Override
    public int execute(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        if (source.isExecutedByPlayer()) {
            ServerPlayerEntity p = source.getPlayer();
            if (p == null) {
                log.warn("executed by player but player == null?");
                return 1;
            }
            Main.getInstance().getProtocolServer().sendPlayerToServer(p.getUuid(), ServerTypeOuterClass.ServerType.HUB);
        }
        return 1;
    }

    @Override
    public LiteralArgumentBuilder<ServerCommandSource> changeArguments(LiteralArgumentBuilder<ServerCommandSource> arguments) {
        return null;
    }
}
