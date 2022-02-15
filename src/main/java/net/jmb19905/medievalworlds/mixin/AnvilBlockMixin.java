package net.jmb19905.medievalworlds.mixin;

import net.jmb19905.medievalworlds.common.block.anvil.CustomAnvilBlock;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilBlock.class)
public class AnvilBlockMixin {

    @Inject(at = @At("RETURN"), method = "damage(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/state/BlockState;")
    private static void damage(BlockState state, CallbackInfoReturnable<BlockState> cir) {
        System.out.println("damaging anvil");
        if(state.getBlock() instanceof CustomAnvilBlock customAnvilBlock) cir.setReturnValue(customAnvilBlock.getDamagedBlockState());
    }

}