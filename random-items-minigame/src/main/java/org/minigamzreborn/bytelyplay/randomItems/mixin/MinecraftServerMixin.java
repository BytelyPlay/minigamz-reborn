package org.minigamzreborn.bytelyplay.randomItems.mixin;

import net.minecraft.server.MinecraftServer;
import org.minigamzreborn.bytelyplay.randomItems.listeners.TickListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        MinecraftServer minecraftServer = (MinecraftServer) (Object) this;
        if (shouldKeepTicking.getAsBoolean()) {
            TickListener.tick(minecraftServer);
        }
    }
}
