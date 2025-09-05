package org.minigamzreborn.bytelyplay.randomItems.mixin;

import net.minecraft.world.gen.GeneratorOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GeneratorOptions.class)
public class GeneratorOptionsMixin {
    @Inject(method = "shouldGenerateStructures", at = @At("HEAD"), cancellable = true)
    public void shouldGenerateStructures(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
