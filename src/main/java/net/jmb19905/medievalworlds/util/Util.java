package net.jmb19905.medievalworlds.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Util {

    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static int percentage(int max, int min, int curr){
        int returnVal;
        if(max > min){
            returnVal = 100/max*curr;
        }else if(max < min){
            curr = Math.abs(curr-min);
            returnVal = 100/min*curr;
        }else{
            throw new IllegalStateException("max ("+max+") and min ("+min+") are the same");
        }
        return returnVal;
    }

    public static RayTraceResult rayTrace(World worldIn, LivingEntity entity, RayTraceContext.FluidMode fluidMode, double reach) {
        float f = entity.rotationPitch;
        float f1 = entity.rotationYaw;
        Vec3d vec3d = entity.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3d vec3d1 = vec3d.add((double)f6 * reach, (double)f5 * reach, (double)f7 * reach);
        return worldIn.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, entity));
    }

    public static List<BlockPos> calcMegaMinerBreaking(ServerWorld world, PlayerEntity player, ItemStack stack, BlockPos origin){
        List<BlockPos> output = new ArrayList<>();
        Direction direction = ((BlockRayTraceResult) Util.rayTrace(world, player, RayTraceContext.FluidMode.ANY, 50)).getFace();
        if (direction == Direction.DOWN || direction == Direction.UP) {
            BlockPos newPos = new BlockPos(origin.getX() - 1, origin.getY(), origin.getZ() - 1);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    BlockPos currentPos = new BlockPos(newPos.getX() + i, newPos.getY(), newPos.getZ() + j);
                    if (stack.canHarvestBlock(world.getBlockState(currentPos)) && world.getBlockState(currentPos).getBlock() != Blocks.BEDROCK) {
                        output.add(currentPos);
                    }
                }
            }
        } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            BlockPos newPos = new BlockPos(origin.getX() - 1, origin.getY() - 1, origin.getZ());
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    BlockPos currentPos = new BlockPos(newPos.getX() + i, newPos.getY() + j, newPos.getZ());
                    if (stack.canHarvestBlock(world.getBlockState(currentPos)) && world.getBlockState(currentPos).getBlock() != Blocks.BEDROCK) {
                        output.add(currentPos);
                    }
                }
            }
        } else {
            BlockPos newPos = new BlockPos(origin.getX(), origin.getY() - 1, origin.getZ() - 1);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    BlockPos currentPos = new BlockPos(newPos.getX(), newPos.getY() + j, newPos.getZ() + i);
                    if (stack.canHarvestBlock(world.getBlockState(currentPos)) && world.getBlockState(currentPos).getBlock() != Blocks.BEDROCK) {
                        output.add(currentPos);
                    }
                }
            }
        }
        return output;
    }
}
