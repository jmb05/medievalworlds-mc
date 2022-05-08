package net.jmb19905.medievalworlds.common.block.anvil;

import net.jmb19905.medievalworlds.common.menus.CustomAnvilMenu;
import net.jmb19905.medievalworlds.common.blockentities.AnvilBlockEntity;
import net.jmb19905.medievalworlds.common.item.ForgeHammerItem;
import net.jmb19905.medievalworlds.core.registries.MWBlockEntityTypes;
import net.jmb19905.medievalworlds.util.CustomItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("deprecation")
public class CustomAnvilBlock extends AnvilBlock implements EntityBlock {

    private final Runnable cooldownHandler;
    private boolean pressed = false;
    private final BlockState damaged;
    private final float damageFactor;

    public CustomAnvilBlock(BlockState damaged, float damageFactor, Properties properties) {
        super(properties);
        this.damaged = damaged;
        this.damageFactor = damageFactor;
        this.cooldownHandler = () -> {
            if(pressed) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        pressed = false;
                    }
                }, 10);
            }
        };
    }

    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if(!level.isClientSide) {
            ItemStack itemInHand = player.getItemInHand(hand);
            if(itemInHand.getItem() instanceof ForgeHammerItem) {
                if(!player.getCooldowns().isOnCooldown(itemInHand.getItem())) {
                    level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.1F + 0.9F);
                    player.getCooldowns().addCooldown(itemInHand.getItem(), 10);
                    pressed = true;
                    cooldownHandler.run();
                    BlockEntity entity = level.getBlockEntity(pos);
                    if (entity instanceof AnvilBlockEntity anvilEntity) anvilEntity.hammer();
                    tryDamage(level, pos);
                    itemInHand.hurtAndBreak(1, player, (living) -> living.broadcastBreakEvent(hand));
                    return InteractionResult.CONSUME;
                }
            } else if(!pressed){
                BlockEntity entity = level.getBlockEntity(pos);
                if (entity instanceof AnvilBlockEntity anvilEntity) {
                    NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) entity, pos);
                    if(player.containerMenu instanceof CustomAnvilMenu anvilMenu)
                        if (anvilEntity.getCurrentRecipe() != null)
                            anvilMenu.setSelectedRecipeIndexByRecipe(anvilEntity.getCurrentRecipe());
                        else
                            anvilMenu.setSelectedRecipeIndexByRecipe(null);
                    return InteractionResult.CONSUME;
                }
            }
        }
        return InteractionResult.PASS;
    }

    public BlockState getDamagedBlockState() {
        return damaged;
    }

    public void tryDamage(Level level, BlockPos pos) {
        Random random = level.getRandom();
        BlockState state = level.getBlockState(pos);
        BlockEntity entity = level.getBlockEntity(pos);
        if(Math.abs(random.nextFloat()) < damageFactor) {
            NonNullList<ItemStack> items = null;
            if(entity instanceof AnvilBlockEntity blockEntity)
                items = ((CustomItemHandler) blockEntity.getInventory()).toNonNullList();
            if(damaged == null) {
                level.destroyBlock(pos, false);
                level.playSound(null, pos, SoundEvents.ANVIL_BREAK, SoundSource.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F);
            }else {
                level.setBlockAndUpdate(pos, damaged.setValue(FACING, state.getValue(FACING)));
                entity = level.getBlockEntity(pos);
                if(entity instanceof AnvilBlockEntity blockEntity && items != null) {
                    ((CustomItemHandler) blockEntity.getInventory()).setNonNullList(items);
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
}
