package net.jmb19905.medievalworlds.common.block.anvil;

import net.jmb19905.medievalworlds.common.blockentities.AnvilBlockEntity;
import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.jmb19905.medievalworlds.util.BlockInteraction;
import net.jmb19905.medievalworlds.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import java.util.Set;
import java.util.function.Predicate;

public interface AnvilInteraction extends BlockInteraction {

    int HAMMER_COOLDOWN = 15;

    Map<Item, AnvilInteraction> EMPTY = BlockInteraction.newInteractionMap();
    Map<Item, AnvilInteraction> FULL_INPUT = BlockInteraction.newInteractionMap();
    Map<Item, AnvilInteraction> FULL_OUTPUT = BlockInteraction.newInteractionMap();

    AnvilInteraction ADD_ITEM = ((state, level, pos, player, hand, stack)
            -> addItem(state, level, pos, stack, (predicateState) -> {
                BlockEntity entity = level.getBlockEntity(pos);
                if(!(entity instanceof AnvilBlockEntity anvilEntity)) return false;
                return anvilEntity.getInventory().getStackInSlot(0) == ItemStack.EMPTY;
            }
    ));

    AnvilInteraction TAKE_ITEM = ((state, level, pos, player, hand, stack)
            -> takeItem(state, level, pos, player, hand, (predicateState) -> {
                BlockEntity entity = level.getBlockEntity(pos);
                if(!(entity instanceof AnvilBlockEntity anvilEntity)) return false;
                if(anvilEntity.getInventory().getStackInSlot(0) != ItemStack.EMPTY) {
                    if(player.getItemInHand(hand).getItem() == Items.AIR) {
                        return true;
                    }else return player.getItemInHand(hand).getItem() == anvilEntity.getItemToShow().getItem();
                }
                return false;
            }
    ));

    AnvilInteraction HAMMER_ITEM = (AnvilInteraction::hammerItem);

    static void bootstrap(Level level){
        Set<ItemStack> inputs = Util.getAllRecipeInputs(MWRecipeSerializers.ANVIL_TYPE, level);
        Set<ItemStack> outputs = Util.getAllRecipeInputs(MWRecipeSerializers.ANVIL_TYPE, level);

        for(ItemStack input : inputs) {
            EMPTY.put(input.getItem(), ADD_ITEM);
            FULL_INPUT.put(input.getItem(), TAKE_ITEM);
        }

        for(ItemStack output : outputs) {
            FULL_INPUT.put(output.getItem(), TAKE_ITEM);
            FULL_OUTPUT.put(output.getItem(), TAKE_ITEM);
        }

        FULL_INPUT.put(Items.AIR, TAKE_ITEM);
        FULL_OUTPUT.put(Items.AIR, TAKE_ITEM);

        FULL_INPUT.put(MWItems.WOODEN_FORGE_HAMMER.get(), HAMMER_ITEM);
        FULL_INPUT.put(MWItems.STONE_FORGE_HAMMER.get(), HAMMER_ITEM);
        FULL_INPUT.put(MWItems.IRON_FORGE_HAMMER.get(), HAMMER_ITEM);
        FULL_INPUT.put(MWItems.STEEL_FORGE_HAMMER.get(), HAMMER_ITEM);
        FULL_INPUT.put(MWItems.DIAMOND_FORGE_HAMMER.get(), HAMMER_ITEM);
        FULL_INPUT.put(MWItems.NETHERITE_FORGE_HAMMER.get(), HAMMER_ITEM);

        FULL_OUTPUT.put(MWItems.WOODEN_FORGE_HAMMER.get(), HAMMER_ITEM);
        FULL_OUTPUT.put(MWItems.STONE_FORGE_HAMMER.get(), HAMMER_ITEM);
        FULL_OUTPUT.put(MWItems.IRON_FORGE_HAMMER.get(), HAMMER_ITEM);
        FULL_OUTPUT.put(MWItems.STEEL_FORGE_HAMMER.get(), HAMMER_ITEM);
        FULL_OUTPUT.put(MWItems.DIAMOND_FORGE_HAMMER.get(), HAMMER_ITEM);
        FULL_OUTPUT.put(MWItems.NETHERITE_FORGE_HAMMER.get(), HAMMER_ITEM);
    }

    static InteractionResult addItem(BlockState state, Level level, BlockPos pos, ItemStack stack, Predicate<BlockState> condition){
        if(!condition.test(state)){
            return InteractionResult.PASS;
        }else if(!level.isClientSide) {
            if(CustomAnvilBlock.setItem(level, pos, new ItemStack(stack.getItem(), 1, stack.getOrCreateTag()))) {
                stack.shrink(1);
                level.setBlockAndUpdate(pos, state);
                level.sendBlockUpdated(pos, state, state, Constants.BlockFlags.BLOCK_UPDATE);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    static InteractionResult takeItem(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, Predicate<BlockState> condition){
        if(!condition.test(state)) {
            return InteractionResult.PASS;
        }else if(!level.isClientSide) {
            player.setItemInHand(hand, CustomAnvilBlock.clearItem(level, pos));
            level.setBlockAndUpdate(pos, state);
            level.sendBlockUpdated(pos, state, state, Constants.BlockFlags.BLOCK_UPDATE);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    static InteractionResult hammerItem(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
        if(!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(blockEntity instanceof AnvilBlockEntity anvilEntity) {
                if(player.isCrouching()) anvilEntity.lockRecipe();
                else anvilEntity.cycleResultItem();
            }
            level.setBlockAndUpdate(pos, state);
            level.sendBlockUpdated(pos, state, state, Constants.BlockFlags.BLOCK_UPDATE);
            player.getCooldowns().addCooldown(stack.getItem(), HAMMER_COOLDOWN);
            stack.hurtAndBreak(1, player, (living) -> living.broadcastBreakEvent(hand));
            level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1,level.getRandom().nextFloat() * 0.1F + 0.9F);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

}