package net.jmb19905.medievalworlds.item;

import net.jmb19905.medievalworlds.item.enchantment.LightningStrikeEnchantment;
import net.jmb19905.medievalworlds.item.enchantment.MegaMinerEnchantment;
import net.jmb19905.medievalworlds.registries.EnchantmentRegistryHandler;
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
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public class HammerItem extends SwordItem {

    private final IItemTier tier;

    public HammerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.tier = tier;
    }

    public float getDestroySpeed(@Nonnull ItemStack stack, BlockState state) {
        Material material = state.getMaterial();
        return material == Material.ROCK || material == Material.IRON ? tier.getEfficiency() + 7 : (EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistryHandler.MEGA_MINER.get(), stack) >= 1) ? 1.5f : 1;
    }

    public boolean canHarvestBlock(@Nonnull BlockState blockIn) {
        return blockIn.getMaterial() == Material.ROCK && blockIn.getHarvestLevel() <= tier.getHarvestLevel();
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
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(entityLiving.getHeldItemMainhand());
        for (Enchantment enchantment : enchantments.keySet()) {
            if (enchantment instanceof MegaMinerEnchantment) {
                if (!worldIn.isRemote && stack.canHarvestBlock(state) && state.getBlock() != Blocks.BEDROCK) {
                    ServerWorld world = (ServerWorld) worldIn;
                    world.setBlockState(pos, state);
                    List<BlockPos> blocks = Util.calcMegaMinerBreaking(world, (PlayerEntity) entityLiving, stack, pos);
                    for(BlockPos pos1 : blocks){
                        if (pos1 != pos) {
                            world.getBlockState(pos1).getBlock().harvestBlock(world, (PlayerEntity) entityLiving, pos1, world.getBlockState(pos1), world.getTileEntity(pos1), stack);
                        }
                        world.setBlockState(pos1, Blocks.AIR.getDefaultState());
                    }
                    entityLiving.getHeldItemMainhand().damageItem(blocks.size() - 1, entityLiving, (living) -> living.sendBreakAnimation(EquipmentSlotType.MAINHAND));
                }
                break;
            }
        }
        return true;
    }
}