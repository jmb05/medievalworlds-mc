package net.jmb19905.medievalworlds.common.block.anvil;

import net.jmb19905.medievalworlds.common.blockentities.anvil.CustomAnvilBlockEntity;
import net.jmb19905.medievalworlds.util.BlockInteraction;
import net.jmb19905.medievalworlds.util.CustomItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class CustomAnvilBlock extends AnvilBlock implements EntityBlock {

    public CustomAnvilBlock(Properties properties) {
        super(properties);
    }

    public static boolean setItem(Level level, BlockPos pos, Item item) {
        BlockEntity entity = level.getBlockEntity(pos);
        if(!(entity instanceof CustomAnvilBlockEntity anvilEntity)){
            return false;
        }
        anvilEntity.getInventory().setStackInSlot(0, new ItemStack(item));
        return true;
    }

    public static Item clearItem(Level level, BlockPos pos) {
        BlockEntity entity = level.getBlockEntity(pos);
        if(!(entity instanceof CustomAnvilBlockEntity anvilEntity)) return Items.AIR;
        Item anvilStack = anvilEntity.getInventory().getStackInSlot(0).getItem();
        anvilEntity.getInventory().setStackInSlot(0, ItemStack.EMPTY);
        return anvilStack;
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        BlockInteraction interaction = AnvilInteraction.INTERACTIONS.get(stack.getItem());
        player.awardStat(Stats.INTERACT_WITH_ANVIL);
        return interaction.interact(state, level, pos, player, hand, stack);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean p_60519_) {
        BlockEntity entity = level.getBlockEntity(pos);
        if(entity instanceof CustomAnvilBlockEntity anvilEntity && state.getBlock() != newState.getBlock()){
            ((CustomItemHandler) anvilEntity.getInventory()).toNonNullList().forEach(stack -> {
                ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
                level.addFreshEntity(itemEntity);
            });
        }

        if(state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
            level.removeBlockEntity(pos);
        }
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos) {
        return null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new CustomAnvilBlockEntity(pos, state);
    }
}
