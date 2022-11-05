package net.jmb19905.medievalworlds.common.item.weapon;

import net.jmb19905.medievalworlds.common.item.MWItemHelper;
import net.jmb19905.medievalworlds.common.item.entchantment.LightningStrikeEnchantment;
import net.jmb19905.medievalworlds.common.item.entchantment.MegaMinerEnchantment;
import net.jmb19905.medievalworlds.common.registries.MWEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("deprecation")
public class HammerItem extends MWSwordWeapon {

    private final Tier tier;

    public HammerItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, -1, builder);
        this.tier = tier;
    }

    @Override
    public boolean canAttackBlock(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player) {
        return true;
    }

    public float getDestroySpeed(@Nonnull ItemStack stack, BlockState state) {
        Material material = state.getMaterial();
        return material == Material.STONE || material == Material.METAL ? tier.getSpeed() + 7 : (EnchantmentHelper.getItemEnchantmentLevel(MWEnchantments.MEGA_MINER.get(), stack) >= 1) ? 1.5f : 1;
    }

    @Override
    public boolean isCorrectToolForDrops(@Nonnull BlockState state) {
        int blockTier = 0;
        if(state.is(BlockTags.NEEDS_STONE_TOOL)) {
            blockTier = 1;
        }else if(state.is(BlockTags.NEEDS_IRON_TOOL)) {
            blockTier = 2;
        }else if(state.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
            blockTier = 3;
        }
        return state.getMaterial() == Material.STONE && blockTier <= tier.getLevel();
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player playerIn, @Nonnull InteractionHand handIn) {
        if(!level.isClientSide) {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(playerIn.getItemInHand(handIn));
            for (Enchantment enchantment : enchantments.keySet()) {
                if (enchantment instanceof LightningStrikeEnchantment) {
                    if(!playerIn.isCreative()) {
                        playerIn.getCooldowns().addCooldown(this, (-(enchantments.get(enchantment) * 10) + 40) * 20);
                    }else{
                        playerIn.getCooldowns().addCooldown(this, ((-(enchantments.get(enchantment) * 10) + 40) * 20) / 2);
                    }
                    BlockHitResult rayTrace = MWItemHelper.rayTraceBlocks(level, playerIn, ClipContext.Fluid.NONE, enchantments.get(enchantment) * 10);
                    LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(level);
                    assert lightningbolt != null;
                    lightningbolt.moveTo(Vec3.atCenterOf(rayTrace.getBlockPos()));
                    lightningbolt.setCause(playerIn instanceof ServerPlayer ? (ServerPlayer)playerIn : null);
                    level.addFreshEntity(lightningbolt);
                    playerIn.getItemInHand(handIn).hurtAndBreak(1, playerIn, (living) -> living.broadcastBreakEvent(handIn == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND));
                    return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
                }
            }
        }
        return InteractionResultHolder.fail(playerIn.getItemInHand(handIn));
    }

    @Override
    public boolean mineBlock(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockPos pos, LivingEntity player) {
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(player.getMainHandItem());
        for (Enchantment enchantment : enchantments.keySet()) {
            if (enchantment instanceof MegaMinerEnchantment) {
                if (!level.isClientSide && stack.isCorrectToolForDrops(state) && state.getBlock() != Blocks.BEDROCK) {
                    ServerLevel serverLevel = (ServerLevel) level;
                    serverLevel.setBlockAndUpdate(pos, state);
                    List<BlockPos> blocks = calcMegaMinerBreaking(serverLevel, (Player) player, stack, pos);
                    for(BlockPos pos1 : blocks){
                        if (pos1 != pos) {
                            serverLevel.getBlockState(pos1).getBlock().playerDestroy(level, (Player) player, pos1, serverLevel.getBlockState(pos1), serverLevel.getBlockEntity(pos1), stack);
                        }
                        serverLevel.setBlockAndUpdate(pos1, Blocks.AIR.defaultBlockState());
                    }
                    stack.hurtAndBreak(blocks.size() - 1, player, (living) -> living.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                }
                break;
            }
        }
        return true;
    }

    public static List<BlockPos> calcMegaMinerBreaking(ServerLevel level, Player player, ItemStack stack, BlockPos origin){
        List<BlockPos> output = new ArrayList<>();
        Direction direction = MWItemHelper.rayTraceBlocks(level, player, ClipContext.Fluid.ANY, 50).getDirection();
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