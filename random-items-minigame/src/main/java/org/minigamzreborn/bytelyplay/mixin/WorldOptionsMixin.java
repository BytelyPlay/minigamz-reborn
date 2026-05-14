package org.minigamzreborn.bytelyplay.mixin;

import net.minecraft.world.level.levelgen.WorldOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldOptions.class)
public class WorldOptionsMixin {
    @Inject(method = "generateStructures", at = @At("HEAD"), cancellable = true)
    public void shouldGenerateStructures(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
