package org.minigamzreborn.bytelyplay.randomItems.mixin;

import net.minecraft.world.ChunkRegion;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.Blender;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.noise.NoiseConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(NoiseChunkGenerator.class)
public class NoiseChunkGeneratorMixin {
    @Inject(at = @At("HEAD"), cancellable = true, method = "buildSurface(Lnet/minecraft/world/ChunkRegion;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/noise/NoiseConfig;Lnet/minecraft/world/chunk/Chunk;)V")
    public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk, CallbackInfo ci) {
        ci.cancel();
    }
    @Inject(at = @At("HEAD"), cancellable = true, method = "populateNoise(Lnet/minecraft/world/gen/chunk/Blender;Lnet/minecraft/world/gen/noise" +
            "/NoiseConfig;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/chunk/Chunk;)Ljava/util/concurrent/CompletableFuture;")
    public void populateNoise(Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk, CallbackInfoReturnable<CompletableFuture<Chunk>> cir) {
        cir.setReturnValue(CompletableFuture.completedFuture(chunk));
    }
    @Inject(at = @At("HEAD"), cancellable = true, method = "populateNoise(Lnet/minecraft/world/gen/chunk/Blender;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/noise/NoiseConfig;Lnet/minecraft/world/chunk/Chunk;II)Lnet/minecraft/world/chunk/Chunk;")
    public void populateNoise(Blender blender, StructureAccessor structureAccessor, NoiseConfig noiseConfig, Chunk chunk, int minimumCellY, int cellHeight, CallbackInfoReturnable<Chunk> cir) {
        cir.setReturnValue(chunk);
    }
    @Inject(at = @At("HEAD"), cancellable = true, method = "populateBiomes(Lnet/minecraft/world/gen/noise/NoiseConfig;Lnet/minecraft/world/gen/chunk/Blender;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/chunk/Chunk;)Ljava/util/concurrent/CompletableFuture;")
    public void populateBiomes(NoiseConfig noiseConfig, Blender blender, StructureAccessor structureAccessor, Chunk chunk, CallbackInfoReturnable<CompletableFuture<Chunk>> cir) {
        cir.setReturnValue(CompletableFuture.completedFuture(chunk));
    }
    @Inject(at = @At("HEAD"), cancellable = true, method = "populateNoise(Lnet/minecraft/world/gen/chunk/Blender;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/noise/NoiseConfig;Lnet/minecraft/world/chunk/Chunk;II)Lnet/minecraft/world/chunk/Chunk;")
    public void populateBiomes(Blender blender, StructureAccessor structureAccessor, NoiseConfig noiseConfig, Chunk chunk, int minimumCellY, int cellHeight, CallbackInfoReturnable<Chunk> cir) {
        cir.setReturnValue(chunk);
    }
    @Inject(at = @At("HEAD"), cancellable = true, method = "populateEntities")
    public void populateEntities(ChunkRegion region, CallbackInfo ci) {
        ci.cancel();
    }
}
