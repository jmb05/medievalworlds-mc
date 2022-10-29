package net.jmb19905.medievalworlds.common.block;

import net.jmb19905.medievalworlds.common.blockentities.AnvilBlockEntity;
import net.jmb19905.medievalworlds.common.item.ForgeHammerItem;
import net.jmb19905.medievalworlds.common.menus.CustomAnvilMenu;
import net.jmb19905.medievalworlds.common.registries.MWBlockEntityTypes;
import net.jmb19905.medievalworlds.util.MWItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class CustomAnvilBlock extends AnvilBlock implements EntityBlock {
    private final BlockState damaged;
    private final float damageFactor;

    public CustomAnvilBlock(BlockState damaged, float damageFactor, Properties properties) {
        super(properties);
        this.damaged = damaged;
        this.damageFactor = damageFactor;
    }

    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if(!level.isClientSide) {
            ItemStack itemInHand = player.getItemInHand(hand);
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof AnvilBlockEntity blockEntity) {
                if (itemInHand.getItem() instanceof ForgeHammerItem) {
                    if (!player.getCooldowns().isOnCooldown(itemInHand.getItem())) {
                        level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.1F + 0.9F);
                        player.getCooldowns().addCooldown(itemInHand.getItem(), 10);
                        blockEntity.hammer();
                        tryDamage(level, pos);
                        itemInHand.hurtAndBreak(1, player, (living) -> living.broadcastBreakEvent(hand));
                        return InteractionResult.CONSUME;
                    }
                } else if (!blockEntity.isPressed()) {
                    NetworkHooks.openScreen((ServerPlayer) player, (MenuProvider) entity, pos);
                    if (player.containerMenu instanceof CustomAnvilMenu anvilMenu)
                        if (blockEntity.getCurrentRecipe() != null)
                            anvilMenu.setSelectedRecipeIndexByRecipe(blockEntity.getCurrentRecipe());
                        else
                            anvilMenu.setSelectedRecipeIndexByRecipe(null);
                    return InteractionResult.CONSUME;
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    public BlockState getDamagedBlockState() {
        return damaged;
    }

    public void tryDamage(Level level, BlockPos pos) {
        RandomSource random = level.getRandom();
        BlockState state = level.getBlockState(pos);
        BlockEntity entity = level.getBlockEntity(pos);
        if(Math.abs(random.nextFloat()) < damageFactor) {
            NonNullList<ItemStack> items = null;
            if(entity instanceof AnvilBlockEntity blockEntity)
                items = ((MWItemHandler) blockEntity.getInventory()).toNonNullList();
            if(damaged == null) {
                level.destroyBlock(pos, false);
                level.playSound(null, pos, SoundEvents.ANVIL_BREAK, SoundSource.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F);
            }else {
                level.setBlockAndUpdate(pos, damaged.setValue(FACING, state.getValue(FACING)));
                entity = level.getBlockEntity(pos);
                if(entity instanceof AnvilBlockEntity blockEntity && items != null) {
                    ((MWItemHandler) blockEntity.getInventory()).setNonNullList(items);
                }
            }
        }
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean b) {
        BlockEntity entity = level.getBlockEntity(pos);
        if(entity instanceof AnvilBlockEntity anvilEntity && state.getBlock() != newState.getBlock() && (damaged == null || newState.getBlock() != damaged.getBlock())){
            ItemEntity itemEntity1 = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), anvilEntity.getInventory().getStackInSlot(0));
            level.addFreshEntity(itemEntity1);
            ItemEntity itemEntity2 = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), anvilEntity.getInventory().getStackInSlot(1));
            level.addFreshEntity(itemEntity2);
        }
        super.onRemove(state, level, pos, newState, b);
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos) {
        return (AnvilBlockEntity) level.getBlockEntity(pos);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return MWBlockEntityTypes.CUSTOM_ANVIL.get().create(pos, state);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return level.isClientSide() ? null : type == MWBlockEntityTypes.CUSTOM_ANVIL.get() ? AnvilBlockEntity::tick : null;
    }
}