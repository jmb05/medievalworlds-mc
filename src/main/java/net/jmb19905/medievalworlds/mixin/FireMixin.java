package net.jmb19905.medievalworlds.mixin;

import net.jmb19905.medievalworlds.common.recipes.burn.BurnRecipe;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.jmb19905.medievalworlds.util.MWUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(value = FireBlock.class, remap = false)
public abstract class FireMixin {
    private BlockState getStateWithAge(LevelAccessor p_53438_, BlockPos p_53439_, int p_53440_) {
        BlockState blockstate = BaseFireBlock.getState(p_53438_, p_53439_);
        return blockstate.is(Blocks.FIRE) ? blockstate.setValue(FireBlock.AGE, p_53440_) : blockstate;
    }
    @Inject(method = "tryCatchFire", at = @At("HEAD"), cancellable = true)
    private void tryCatchFire(Level level, BlockPos pos, int flammability, RandomSource random, int burnability, Direction face, CallbackInfo info) {
        int flamm = level.getBlockState(pos).getFlammability(level, pos, face);
        if (random.nextInt(flammability) < flamm) {
            BlockState blockState = level.getBlockState(pos);
            if (random.nextInt(burnability + 10) < 5 && !level.isRainingAt(pos)) {
                int age = Math.min(burnability + random.nextInt(5) / 4, 15);
                if(!transformByBurning(level, pos, random)) level.setBlock(pos, this.getStateWithAge(level, pos, age), 3);
            } else {
                if(!transformByBurning(level, pos, random)) level.removeBlock(pos, false);
            }
            blockState.onCaughtFire(level, pos, face, null);
        }
        info.cancel();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean transformByBurning(Level level, BlockPos pos, RandomSource random) {
        BurnRecipe recipe = getRecipe(level, level.getBlockState(pos));
        if(recipe != null) {
            if(random.nextFloat() > .5f) {
                level.setBlock(pos, recipe.getOutput().defaultBlockState(), 3);
                return true;
            }
        }
        return false;
    }

    private BurnRecipe getRecipe(Level level, BlockState block) {
        if(block == null){
            return null;
        }
        Set<Recipe<?>> recipes = MWUtil.findRecipeByType(MWRecipeSerializers.BURN_TYPE, level);
        for(Recipe<?> iRecipe : recipes){
            BurnRecipe recipe = (BurnRecipe)iRecipe;
            if(recipe.matches(block)) return recipe;
        }
        return null;
    }

}
