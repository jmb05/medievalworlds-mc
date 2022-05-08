package net.jmb19905.medievalworlds.common.block;

import net.jmb19905.medievalworlds.common.multiblock.ForgeShape;
import net.jmb19905.medievalworlds.common.recipes.burn.BurnRecipe;
import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.jmb19905.medievalworlds.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.PortalShape;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class CustomFireBlock extends FireBlock {

    public CustomFireBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public void tick(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull Random random) {
        level.scheduleTick(pos, this, getFireTickDelay(level.random));
        if (level.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            if (!state.canSurvive(level, pos)) level.removeBlock(pos, false);

            BlockState belowState = level.getBlockState(pos.below());
            boolean fireSourceBelow = belowState.isFireSource(level, pos, Direction.UP);
            int fireAge = state.getValue(AGE);
            if (!fireSourceBelow && level.isRaining() && this.isNearRain(level, pos) && random.nextFloat() < 0.2F + (float)fireAge * 0.03F) {
                level.removeBlock(pos, false);
            } else {
                int minAge = Math.min(15, fireAge + random.nextInt(3) / 2);
                if (fireAge != minAge) {
                    state = state.setValue(AGE, minAge);
                    level.setBlock(pos, state, 4);
                }

                if (!fireSourceBelow) {
                    if (!this.isValidFireLocation(level, pos)) {
                        BlockPos belowPos = pos.below();
                        if (!level.getBlockState(belowPos).isFaceSturdy(level, belowPos, Direction.UP) || fireAge > 3) {
                            level.removeBlock(pos, false);
                        }
                        return;
                    }

                    if (fireAge == 15 && random.nextInt(4) == 0 && !this.canCatchFire(level, pos.below(), Direction.UP)) {
                        level.removeBlock(pos, false);
                        return;
                    }
                }

                boolean humid = level.isHumidAt(pos);
                int humidityFactor = humid ? -50 : 0;
                this.tryCatchFire(level, pos.east(), 300 + humidityFactor, random, fireAge, Direction.WEST);
                this.tryCatchFire(level, pos.west(), 300 + humidityFactor, random, fireAge, Direction.EAST);
                this.tryCatchFire(level, pos.below(), 250 + humidityFactor, random, fireAge, Direction.UP);
                this.tryCatchFire(level, pos.above(), 250 + humidityFactor, random, fireAge, Direction.DOWN);
                this.tryCatchFire(level, pos.north(), 300 + humidityFactor, random, fireAge, Direction.SOUTH);
                this.tryCatchFire(level, pos.south(), 300 + humidityFactor, random, fireAge, Direction.NORTH);
                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for(int l = -1; l <= 1; ++l) {
                    for(int i1 = -1; i1 <= 1; ++i1) {
                        for(int j1 = -1; j1 <= 4; ++j1) {
                            if (l != 0 || j1 != 0 || i1 != 0) {
                                int k1 = 100;
                                if (j1 > 1) {
                                    k1 += (j1 - 1) * 100;
                                }

                                blockpos$mutableblockpos.setWithOffset(pos, l, j1, i1);
                                int l1 = this.getFireOdds(level, blockpos$mutableblockpos);
                                if (l1 > 0) {
                                    int i2 = (l1 + 40 + level.getDifficulty().getId() * 7) / (fireAge + 30);
                                    if (humid) {
                                        i2 /= 2;
                                    }

                                    if (i2 > 0 && random.nextInt(k1) <= i2 && (!level.isRaining() || !this.isNearRain(level, blockpos$mutableblockpos))) {
                                        int j2 = Math.min(15, fireAge + random.nextInt(5) / 4);
                                        level.setBlock(blockpos$mutableblockpos, this.getStateWithAge(level, blockpos$mutableblockpos, j2), 3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void tryCatchFire(Level level, BlockPos pos, int flammability, Random random, int burnability, Direction face) {
        int flammability2 = level.getBlockState(pos).getFlammability(level, pos, face);
        if (random.nextInt(flammability) < flammability2) {
            BlockState blockstate = level.getBlockState(pos);
            if (random.nextInt(burnability + 10) < 5 && !level.isRainingAt(pos)) {
                int age = Math.min(burnability + random.nextInt(5) / 4, 15);
                if(!transformByBurning(level, pos)) level.setBlock(pos, this.getStateWithAge(level, pos, age), 3);
            } else {
                if(!transformByBurning(level, pos)) level.removeBlock(pos, false);
            }

            blockstate.onCaughtFire(level, pos, face, null);
        }

    }

    private BlockState getStateWithAge(LevelAccessor level, BlockPos pos, int age) {
        BlockState blockstate = getState(level, pos);
        return blockstate.is(Blocks.FIRE) ? blockstate.setValue(AGE, age) : blockstate;
    }

    private boolean isValidFireLocation(BlockGetter blockGetter, BlockPos pos) {
        for(Direction direction : Direction.values()) {
            if (this.canCatchFire(blockGetter, pos.relative(direction), direction.getOpposite())) {
                return true;
            }
        }

        return false;
    }

    private int getFireOdds(LevelReader level, BlockPos pos) {
        if (!level.isEmptyBlock(pos)) return 0;
        else {
            int i = 0;

            for(Direction direction : Direction.values()) {
                BlockState blockstate = level.getBlockState(pos.relative(direction));
                i = Math.max(blockstate.getFireSpreadSpeed(level, pos.relative(direction), direction.getOpposite()), i);
            }

            return i;
        }
    }

    private static int getFireTickDelay(Random random) {
        return 30 + random.nextInt(10);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean transformByBurning(Level level, BlockPos pos) {
        BurnRecipe recipe = getRecipe(level, level.getBlockState(pos));
        if(recipe != null) {
            Random random = level.getRandom();
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
        Set<Recipe<?>> recipes = Util.findRecipeByType(MWRecipeSerializers.BURN_TYPE, level);
        for(Recipe<?> iRecipe : recipes){
            BurnRecipe recipe = (BurnRecipe)iRecipe;
            if(recipe.matches(block)) return recipe;
        }
        return null;
    }

    public static void bootstrap() {
        FireBlock.bootStrap();
    }

    public void onPlace(@NotNull BlockState oldState, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean b) {
        super.onPlace(oldState, level, pos, newState, b);
        if (!newState.is(oldState.getBlock())) {
            if (inPortalDimension(level)) {
                Optional<PortalShape> optional = PortalShape.findEmptyPortalShape(level, pos, Direction.Axis.X);
                optional = net.minecraftforge.event.ForgeEventFactory.onTrySpawnPortal(level, pos, optional);
                if (optional.isPresent()) {
                    optional.get().createPortalBlocks();
                    return;
                }
            }

            Optional<ForgeShape> optionalForgeShape = ForgeShape.findForgeShape(level, pos);
            if(optionalForgeShape.isPresent()) {
                optionalForgeShape.get().form();
                return;
            }

            if (!oldState.canSurvive(level, pos)) {
                level.removeBlock(pos, false);
            }
        }
    }

    private static boolean inPortalDimension(Level level) {
        return level.dimension() == Level.OVERWORLD || level.dimension() == Level.NETHER;
    }

}
