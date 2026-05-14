package org.minigamzreborn.bytelyplay.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelData;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.UnregisterServerPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.protobuffer.packets.c2s.WrappedPacketC2SOuterClass;
import org.minigamzreborn.bytelyplay.Main;
import org.minigamzreborn.bytelyplay.listeners.ServerStartedListener;
import org.minigamzreborn.bytelyplay.listeners.TickListener;
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
    public abstract void setRespawnData(LevelData.RespawnData respawnData);

    @Inject(at = @At("HEAD"), method = "tickServer")
    public void tick(BooleanSupplier haveTime, CallbackInfo ci) {
        MinecraftServer minecraftServer = (MinecraftServer) (Object) this;
        if (haveTime.getAsBoolean()) {
            TickListener.tick(minecraftServer);
        }
    }
    @Inject(at = @At("RETURN"), method = "spin")
    private static void startServer(Function<Thread, MinecraftServer> factory, CallbackInfoReturnable<MinecraftServer> cir) {
        ServerStartedListener.started(cir.getReturnValue());
    }
    @Inject(at = @At("HEAD"), method = "stopServer")
    private void stop(CallbackInfo ci) {
        Main.getInstance().getProtocolServer().sendPacket(WrappedPacketC2SOuterClass.WrappedPacketC2S.newBuilder()
                .setUnregisterServerPacket(UnregisterServerPacketC2SOuterClass.UnregisterServerPacketC2S.newBuilder()
                        // TODO: Make configurable
                        .setIp(Main.getMinecraftServer().getLocalIp())
                        .setPort(Main.getMinecraftServer().getPort())
                        .build())
                .build());
        Main.getInstance().getProtocolServer().disconnect();
    }
    @Inject(at = @At("RETURN"), method = "loadLevel")
    public void loadWorld(CallbackInfo ci) {
        // TODO: Make configurable
        this.setRespawnData(LevelData.RespawnData.of(
                Level.OVERWORLD,
                new BlockPos(0, 3, 0), 90f,
                0f
        ));
    }
}
