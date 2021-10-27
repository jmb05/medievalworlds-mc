package net.jmb19905.medievalworlds.common.block.anvil;

import net.jmb19905.medievalworlds.common.blockentities.anvil.CustomAnvilBlockEntity;
import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.jmb19905.medievalworlds.util.BlockInteraction;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.Constants;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface AnvilInteraction extends BlockInteraction {
    Map<Item, AnvilInteraction> INTERACTIONS = BlockInteraction.newInteractionMap();

    AnvilInteraction ADD_ITEM = ((state, level, pos, player, hand, stack)
            -> addItem(state, level, pos, stack, (predicateState) -> {
                BlockEntity entity = level.getBlockEntity(pos);
                if(!(entity instanceof CustomAnvilBlockEntity anvilEntity)) return false;
                return anvilEntity.getInventory().getStackInSlot(0).equals(ItemStack.EMPTY);
            }
    ));

    AnvilInteraction TAKE_ITEM = ((state, level, pos, player, hand, stack)
            -> takeItem(state, level, pos, player, hand, (predicateState) -> {
                BlockEntity entity = level.getBlockEntity(pos);
                if(!(entity instanceof CustomAnvilBlockEntity anvilEntity)) return false;
                return !anvilEntity.getInventory().getStackInSlot(0).equals(ItemStack.EMPTY);
            }
    ));

    AnvilInteraction HAMMER_ITEM = (AnvilInteraction::hammerItem);

    static void bootstrap(){
        INTERACTIONS.put(Items.IRON_INGOT, ADD_ITEM);
        INTERACTIONS.put(Items.AIR, TAKE_ITEM);
        INTERACTIONS.put(MWItems.WOODEN_FORGE_HAMMER.get(), HAMMER_ITEM);
        INTERACTIONS.put(MWItems.STONE_FORGE_HAMMER.get(), HAMMER_ITEM);
        INTERACTIONS.put(MWItems.IRON_FORGE_HAMMER.get(), HAMMER_ITEM);
        INTERACTIONS.put(MWItems.STEEL_FORGE_HAMMER.get(), HAMMER_ITEM);
        INTERACTIONS.put(MWItems.DIAMOND_FORGE_HAMMER.get(), HAMMER_ITEM);
        INTERACTIONS.put(MWItems.NETHERITE_FORGE_HAMMER.get(), HAMMER_ITEM);
    }

    static InteractionResult addItem(BlockState state, Level level, BlockPos pos, ItemStack stack, Predicate<BlockState> condition){
        if(!condition.test(state)){
            return InteractionResult.PASS;
        }else {
            if(!level.isClientSide) {
                if(CustomAnvilBlock.setItem(level, pos, stack.getItem())) {
                    stack.shrink(1);
                    level.setBlockAndUpdate(pos, state);
                    level.sendBlockUpdated(pos, state, state, Constants.BlockFlags.BLOCK_UPDATE);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    static InteractionResult takeItem(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, Predicate<BlockState> condition){
        if(!condition.test(state)) {
            return InteractionResult.PASS;
        }else {
            if(!level.isClientSide) {
                player.setItemInHand(hand, new ItemStack(CustomAnvilBlock.clearItem(level, pos)));
                level.setBlockAndUpdate(pos, state);
                level.sendBlockUpdated(pos, state, state, Constants.BlockFlags.BLOCK_UPDATE);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    static InteractionResult hammerItem(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
        if(!level.isClientSide) {
            stack.hurtAndBreak(1, player, (living) -> living.broadcastBreakEvent(hand));
            level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1,level.getRandom().nextFloat() * 0.1F + 0.9F);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

}
