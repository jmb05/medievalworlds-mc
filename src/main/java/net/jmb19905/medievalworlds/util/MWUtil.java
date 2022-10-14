package net.jmb19905.medievalworlds.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MWUtil {

    public static List<Recipe<?>> findRecipeByTypeAsList(RecipeType<?> type, Level level) {
        return new ArrayList<>(MWUtil.findRecipeByType(type, level));
    }

    @SuppressWarnings("unchecked")
    public static Set<Recipe<?>> findRecipeByType(RecipeType<?> typeIn, Level world) {
        return world != null ? world.getRecipeManager().getRecipes()
                .stream()
                .filter(recipe -> recipe.getType() == typeIn)
                .collect(Collectors.toSet())
                : Collections.EMPTY_SET;
    }

    @SuppressWarnings("unchecked")
    @OnlyIn(Dist.CLIENT)
    public static Set<Recipe<?>> findRecipeByType(RecipeType<?> typeIn) {
        ClientLevel world = Minecraft.getInstance().level;
        return world != null ? world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()) : Collections.EMPTY_SET;
    }

    //attacker code is from Item#getPlayerPOVHitResult
    public static BlockHitResult rayTraceBlocks(Level level, LivingEntity entity, ClipContext.Fluid fluidMode, double reach) {
        float f = entity.getXRot();
        float f1 = entity.getYRot();
        Vec3 vec3 = entity.getEyePosition();
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3 vec31 = vec3.add((double)f6 * reach, (double)f5 * reach, (double)f7 * reach);
        return level.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, fluidMode, entity));
    }

    public static List<BlockPos> calcMegaMinerBreaking(ServerLevel level, Player player, ItemStack stack, BlockPos origin){
        List<BlockPos> output = new ArrayList<>();
        Direction direction = rayTraceBlocks(level, player, ClipContext.Fluid.ANY, 50).getDirection();
        if (direction == Direction.DOWN || direction == Direction.UP) {
            BlockPos newPos = new BlockPos(origin.getX() - 1, origin.getY(), origin.getZ() - 1);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    BlockPos currentPos = new BlockPos(newPos.getX() + i, newPos.getY(), newPos.getZ() + j);
                    var breakable = level.getBlockState(currentPos).getDestroySpeed(level, currentPos) >= 0;
                    if (stack.isCorrectToolForDrops(level.getBlockState(currentPos)) && breakable) {
                        output.add(currentPos);
                    }
                }
            }
        } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            BlockPos newPos = new BlockPos(origin.getX() - 1, origin.getY() - 1, origin.getZ());
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    BlockPos currentPos = new BlockPos(newPos.getX() + i, newPos.getY() + j, newPos.getZ());
                    var breakable = level.getBlockState(currentPos).getDestroySpeed(level, currentPos) >= 0;
                    if (stack.isCorrectToolForDrops(level.getBlockState(currentPos)) && breakable) {
                        output.add(currentPos);
                    }
                }
            }
        } else {
            BlockPos newPos = new BlockPos(origin.getX(), origin.getY() - 1, origin.getZ() - 1);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    BlockPos currentPos = new BlockPos(newPos.getX(), newPos.getY() + j, newPos.getZ() + i);
                    var breakable = level.getBlockState(currentPos).getDestroySpeed(level, currentPos) >= 0;
                    if (stack.isCorrectToolForDrops(level.getBlockState(currentPos)) && breakable) {
                        output.add(currentPos);
                    }
                }
            }
        }
        return output;
    }

}
