package org.minigamzreborn.bytelyplay.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(NoiseBasedChunkGenerator.class)
public class NoiseBasedChunkGeneratorMixin {
    @Shadow
    @Final
    private Holder<NoiseGeneratorSettings> settings;

    @Inject(at = @At("HEAD"), cancellable = true, method = "lambda$createFluidPicker$0")
    private static void createFluidPicker$lambda(Aquifer.FluidStatus emptyStatus,
                                          int seaLevel, Aquifer.FluidStatus lavaStatus,
                                          Aquifer.FluidStatus seaStatus, int x, int y, int z,
                                          CallbackInfoReturnable<Aquifer.FluidStatus> cir) {
        cir.setReturnValue(emptyStatus);
    }

    @Inject(at = @At("HEAD"), cancellable = true, method = "buildSurface(Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/world/level/levelgen/WorldGenerationContext;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/biome/BiomeManager;Lnet/minecraft/core/Registry;Lnet/minecraft/world/level/levelgen/blending/Blender;)V")
    public void buildSurface(
            ChunkAccess chunk,
            WorldGenerationContext context,
            RandomState randomState,
            StructureManager structureManager,
            BiomeManager biomeManager,
            Registry<Biome> biomeRegistry,
            Blender blender,
            CallbackInfo ci
    ) {
        int chunkX = chunk.getPos().x();
        int chunkY = chunk.getPos().z();

        if (chunkX == 0 && chunkY == 0) {
            for (int x = 0; x < 3; x++) {
                for (int z = 0; z < 3; z++) {
                    chunk.setBlockState(new BlockPos(x, 0, z), Blocks.BEDROCK.defaultBlockState());
                }
            }
        }
    }
    @Inject(at = @At("HEAD"), cancellable = true, method = "fillFromNoise")
    public void populateNoise(
            Blender blender,
                              RandomState randomState,
                              StructureManager structureManager,
                              ChunkAccess chunk,
                              CallbackInfoReturnable<CompletableFuture<ChunkAccess>> cir
    ) {
        cir.setReturnValue(CompletableFuture.completedFuture(chunk));
    }
    @Inject(at = @At("HEAD"), cancellable = true, method = "fillFromNoise")
    public void fillFromNoise(Blender blender, RandomState randomState,
                              StructureManager structureManager, ChunkAccess chunk, CallbackInfoReturnable<CompletableFuture<ChunkAccess>> cir) {
        cir.setReturnValue(CompletableFuture.completedFuture(chunk));
    }
    @Inject(at = @At("HEAD"), cancellable = true, method = "createBiomes")
    public void createBiomes(RandomState randomState, Blender blender, StructureManager structureManager, ChunkAccess chunk, CallbackInfoReturnable<CompletableFuture<ChunkAccess>> cir) {
        cir.setReturnValue(CompletableFuture.completedFuture(chunk));
    }
    @Inject(at = @At("HEAD"), cancellable = true, method = "doCreateBiomes")
    public void doCreateBiomes(Blender blender,
                               RandomState randomState,
                               StructureManager structureManager,
                               ChunkAccess protoChunk, CallbackInfo ci) {
        ci.cancel();
    }
    @Inject(at = @At("HEAD"), cancellable = true, method = "spawnOriginalMobs")
    public void spawnOriginalMobs(WorldGenRegion worldGenRegion, CallbackInfo ci) {
        ci.cancel();
    }
    @Inject(at = @At("HEAD"), cancellable = true, method = "applyCarvers")
    public void carve(WorldGenRegion region, long seed, RandomState randomState, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunk, CallbackInfo ci) {
        ci.cancel();
    }
}
