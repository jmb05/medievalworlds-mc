package net.jmb19905.medievalworlds.common.block.anvil;

import net.jmb19905.medievalworlds.common.blockentities.AnvilBlockEntity;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

@SuppressWarnings("deprecation")
public class CustomAnvilBlock extends AnvilBlock implements EntityBlock {

    public CustomAnvilBlock(Properties properties) {
        super(properties);
    }

    public static boolean setItem(Level level, BlockPos pos, ItemStack stack) {
        BlockEntity entity = level.getBlockEntity(pos);
        if(!(entity instanceof AnvilBlockEntity anvilEntity)){
            return false;
        }
        anvilEntity.getInventory().setStackInSlot(0, stack);
        return true;
    }

    public static ItemStack clearItem(Level level, BlockPos pos) {
        BlockEntity entity = level.getBlockEntity(pos);
        if(!(entity instanceof AnvilBlockEntity anvilEntity)) return ItemStack.EMPTY;
        ItemStack anvilStack = anvilEntity.getItemToShow();
        anvilEntity.getInventory().setStackInSlot(0, ItemStack.EMPTY);
        anvilEntity.getInventory().setStackInSlot(1, ItemStack.EMPTY);
        return anvilStack;
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hitResult) {
        if(hand == InteractionHand.OFF_HAND) {
            return InteractionResult.PASS;
        }
        ItemStack stack = player.getItemInHand(hand);
        BlockEntity entity = level.getBlockEntity(pos);
        if(entity instanceof AnvilBlockEntity anvilEntity) {
            Map<Item, AnvilInteraction> interactionMap = anvilEntity.hasItems() ? (anvilEntity.hasNoResultItem() ? AnvilInteraction.FULL_INPUT : AnvilInteraction.FULL_OUTPUT) : AnvilInteraction.EMPTY;
            if(interactionMap == AnvilInteraction.FULL_OUTPUT) {
                System.out.println("Interaction: FULL_OUTPUT");
            }else if(interactionMap == AnvilInteraction.FULL_INPUT) {
                System.out.println("Interaction: FULL_INPUT");
            }else {
                System.out.println("Interaction: EMPTY");
            }
            BlockInteraction interaction = interactionMap.get(stack.getItem());
            player.awardStat(Stats.INTERACT_WITH_ANVIL);
            return interaction.interact(state, level, pos, player, hand, stack);
        }else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean p_60519_) {
        BlockEntity entity = level.getBlockEntity(pos);
        if(entity instanceof AnvilBlockEntity anvilEntity && state.getBlock() != newState.getBlock()){
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
        return new AnvilBlockEntity(pos, state);
    }
}
