package net.jmb19905.medievalworlds.item;

import net.jmb19905.medievalworlds.item.enchantment.LightningStrikeEnchantment;
import net.jmb19905.medievalworlds.item.enchantment.MegaMinerEnchantment;
import net.jmb19905.medievalworlds.util.Util;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Map;

public class HammerItem extends SwordItem {

    public HammerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    public float getDestroySpeed(@Nonnull ItemStack stack, BlockState state) {
        Material material = state.getMaterial();
        for(Enchantment enchantment:EnchantmentHelper.getEnchantments(stack).keySet()){
            if(enchantment instanceof MegaMinerEnchantment){
                return material == Material.ROCK || material == Material.IRON ? 15 : 1;
            }
        }
        return 1;
    }

    public boolean canHarvestBlock(@Nonnull BlockState blockIn) {
        return blockIn.getMaterial() == Material.ROCK;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull PlayerEntity playerIn, @Nonnull Hand handIn) {
        if(!worldIn.isRemote) {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(playerIn.getHeldItem(handIn));
            for (Enchantment enchantment : enchantments.keySet()) {
                if (enchantment instanceof LightningStrikeEnchantment) {
                    playerIn.getCooldownTracker().setCooldown(this, (-(enchantments.get(enchantment) * 10) + 40) * 20);
                    BlockRayTraceResult rayTrace = (BlockRayTraceResult) Util.rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.NONE, enchantments.get(enchantment) * 10);
                    ((ServerWorld) worldIn).addLightningBolt(new LightningBoltEntity(worldIn, rayTrace.getPos().getX(), rayTrace.getPos().getY(), rayTrace.getPos().getZ(), false));
                    playerIn.getHeldItem(handIn).damageItem(1, playerIn, (living) -> living.sendBreakAnimation(handIn == Hand.MAIN_HAND ? EquipmentSlotType.MAINHAND : EquipmentSlotType.OFFHAND));
                    return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
                }
            }
        }
        return ActionResult.resultFail(playerIn.getHeldItem(handIn));
    }

    @Override
    public boolean onBlockDestroyed(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull BlockState state, @Nonnull BlockPos pos, @Nonnull LivingEntity entityLiving) {
        int harvestedBlockCount = 0;
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(entityLiving.getHeldItemMainhand());
        for (Enchantment enchantment : enchantments.keySet()) {
            if (enchantment instanceof MegaMinerEnchantment) {
                if (!worldIn.isRemote && stack.canHarvestBlock(state) && state.getBlock() != Blocks.BEDROCK) {
                    ServerWorld world = (ServerWorld) worldIn;
                    world.setBlockState(pos, state);
                    Direction direction = ((BlockRayTraceResult) Util.rayTrace(worldIn, entityLiving, RayTraceContext.FluidMode.ANY, 50)).getFace();
                    if (direction == Direction.DOWN || direction == Direction.UP) {
                        BlockPos newPos = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ() - 1);
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                BlockPos currentPos = new BlockPos(newPos.getX() + i, newPos.getY(), newPos.getZ() + j);
                                if (stack.canHarvestBlock(world.getBlockState(currentPos)) && world.getBlockState(currentPos).getBlock() != Blocks.BEDROCK) {
                                    if (currentPos != pos) {
                                        world.getBlockState(currentPos).getBlock().harvestBlock(worldIn, (PlayerEntity) entityLiving, currentPos, world.getBlockState(currentPos), world.getTileEntity(currentPos), stack);
                                    }
                                    world.setBlockState(currentPos, Blocks.AIR.getDefaultState());
                                    harvestedBlockCount++;
                                }
                            }
                        }
                    } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                        BlockPos newPos = new BlockPos(pos.getX() - 1, pos.getY() - 1, pos.getZ());
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                BlockPos currentPos = new BlockPos(newPos.getX() + i, newPos.getY() + j, newPos.getZ());
                                if (stack.canHarvestBlock(world.getBlockState(currentPos)) && world.getBlockState(currentPos).getBlock() != Blocks.BEDROCK) {
                                    if (currentPos != pos) {
                                        world.getBlockState(currentPos).getBlock().harvestBlock(worldIn, (PlayerEntity) entityLiving, currentPos, world.getBlockState(currentPos), world.getTileEntity(currentPos), stack);
                                    }
                                    world.setBlockState(currentPos, Blocks.AIR.getDefaultState());
                                    harvestedBlockCount++;
                                }
                            }
                        }
                    } else {
                        BlockPos newPos = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ() - 1);
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                BlockPos currentPos = new BlockPos(newPos.getX(), newPos.getY() + j, newPos.getZ() + i);
                                if (stack.canHarvestBlock(world.getBlockState(currentPos)) && world.getBlockState(currentPos).getBlock() != Blocks.BEDROCK) {
                                    if (currentPos != pos) {
                                        world.getBlockState(currentPos).getBlock().harvestBlock(worldIn, (PlayerEntity) entityLiving, currentPos, world.getBlockState(currentPos), world.getTileEntity(currentPos), stack);
                                        //System.out.println("harvesting Extra Block: " + world.getBlockState(currentPos));
                                    }
                                    world.setBlockState(currentPos, Blocks.AIR.getDefaultState());
                                    harvestedBlockCount++;
                                }
                            }
                        }
                    }
                    entityLiving.getHeldItemMainhand().damageItem(harvestedBlockCount-1, entityLiving, (living) -> living.sendBreakAnimation(EquipmentSlotType.MAINHAND));
                }
                break;
            }
        }
        return true;
    }
}