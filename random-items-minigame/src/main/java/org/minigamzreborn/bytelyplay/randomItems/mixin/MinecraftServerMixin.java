package org.minigamzreborn.bytelyplay.randomItems.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.minigamzreborn.bytelyplay.protobuffer.packets.UnregisterServerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.randomItems.Main;
import org.minigamzreborn.bytelyplay.randomItems.listeners.ServerStartedListener;
import org.minigamzreborn.bytelyplay.randomItems.listeners.TickListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BooleanSupplier;
import java.util.function.Function;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Shadow
    public abstract ServerWorld getOverworld();

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        MinecraftServer minecraftServer = (MinecraftServer) (Object) this;
        if (shouldKeepTicking.getAsBoolean()) {
            TickListener.tick(minecraftServer);
        }
    }
    @Inject(at = @At("RETURN"), method = "startServer")
    private static void startServer(Function<Thread, MinecraftServer> serverFactory, CallbackInfoReturnable<MinecraftServer> cir) {
        ServerStartedListener.started(cir.getReturnValue());
    }
    @Inject(at = @At("HEAD"), method = "stop")
    private void stop(boolean waitForShutdown, CallbackInfo ci) {
        Main.getInstance().getProtocolServer().sendPacket(WrappedPacketC2SOuterClass.WrappedPacketC2S.newBuilder()
                .setUnregisterServerPacket(UnregisterServerPacketC2SOuterClass.UnregisterServerPacketC2S.newBuilder()
                        .setIp(Main.getMinecraftServer().getServerIp())
                        .setPort(Main.getMinecraftServer().getServerPort())
                        .build())
                .build());
        Main.getInstance().getProtocolServer().disconnect();
    }
    @Inject(at = @At("RETURN"), method = "loadWorld")
    public void loadWorld(CallbackInfo ci) {
        ServerWorld overworld = this.getOverworld();
        overworld.setSpawnPos(new BlockPos(0, 3, 0), 90f);
    }
}
