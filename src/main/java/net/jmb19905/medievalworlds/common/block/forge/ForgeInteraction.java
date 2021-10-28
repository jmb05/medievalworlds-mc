package net.jmb19905.medievalworlds.common.block.forge;

import net.jmb19905.medievalworlds.common.blockentities.forge.ForgeBaseBlockEntity;
import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.jmb19905.medievalworlds.util.BlockInteraction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.Constants;

import java.util.Map;
import java.util.function.Predicate;

public interface ForgeInteraction extends BlockInteraction {
    Map<Item, ForgeInteraction> INTERACTIONS = BlockInteraction.newInteractionMap();

    ForgeInteraction ADD_ITEM = ((state, level, pos, player, hand, stack)
            -> addItem(state, level, pos, stack, (predicateState) -> {
                BlockEntity entity = level.getBlockEntity(pos);
                if(!(entity instanceof ForgeBaseBlockEntity forgeEntity)) return false;
                return forgeEntity.getInventory().getStackInSlot(0).equals(ItemStack.EMPTY);
            }
    ));

    ForgeInteraction TAKE_ITEM = ((state, level, pos, player, hand, stack)
            -> takeItem(state, level, pos, player, hand, (predicateState) -> {
                BlockEntity entity = level.getBlockEntity(pos);
                if(!(entity instanceof ForgeBaseBlockEntity forgeEntity)) return false;
                return !forgeEntity.getInventory().getStackInSlot(0).equals(ItemStack.EMPTY);
            }
    ));

    static void bootstrap(){
        INTERACTIONS.put(MWItems.SILVER_INGOT.get(), ADD_ITEM);
        INTERACTIONS.put(MWItems.BRONZE_INGOT.get(), ADD_ITEM);
        INTERACTIONS.put(Items.COPPER_INGOT, ADD_ITEM);
        INTERACTIONS.put(MWItems.TIN_INGOT.get(), ADD_ITEM);
        INTERACTIONS.put(MWItems.STEEL_INGOT.get(), ADD_ITEM);
        INTERACTIONS.put(Items.IRON_INGOT, ADD_ITEM);
        INTERACTIONS.put(Items.GOLD_INGOT, ADD_ITEM);
        INTERACTIONS.put(Items.NETHERITE_INGOT, ADD_ITEM);
        INTERACTIONS.put(Items.AIR, TAKE_ITEM);
    }

    static InteractionResult addItem(BlockState state, Level level, BlockPos pos, ItemStack stack, Predicate<BlockState> condition){
        if(!condition.test(state)){
            return InteractionResult.PASS;
        }else {
            if(!level.isClientSide) {
                if(ForgeBaseBlock.setItem(level, pos, stack.getItem())) {
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
                player.setItemInHand(hand, new ItemStack(ForgeBaseBlock.clearItem(level, pos)));
                level.setBlockAndUpdate(pos, state);
                level.sendBlockUpdated(pos, state, state, Constants.BlockFlags.BLOCK_UPDATE);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

}
