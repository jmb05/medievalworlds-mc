package net.jmb19905.medievalworlds.common.block.slackTub;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Map;
import java.util.function.Predicate;

public interface SlackTubInteraction {
    Map<Item, SlackTubInteraction> EMPTY = newInteractionMap();
    Map<Item, SlackTubInteraction> WATER = newInteractionMap();

    SlackTubInteraction FILL_WATER = (state, level, pos, player, hand, stack)
            -> emptyBucket(level, pos, player, hand, stack, MWBlocks.SLACK_TUB_BLOCK.get().setFull(state, level, pos), SoundEvents.BUCKET_EMPTY);

    SlackTubInteraction EMPTY_WATER = (state, level, pos, player, hand, stack)
            -> fillBucket(state, level, pos, player, hand, stack, new ItemStack(Items.WATER_BUCKET), (predicateState) -> MWBlocks.SLACK_TUB_BLOCK.get().isFull(predicateState), SoundEvents.BUCKET_FILL);

    static Object2ObjectOpenHashMap<Item, SlackTubInteraction> newInteractionMap() {
        return Util.make(new Object2ObjectOpenHashMap<>(), (map)
                -> map.defaultReturnValue((p_175739_, p_175740_, p_175741_, p_175742_, p_175743_, p_175744_)
                -> InteractionResult.PASS));
    }

    InteractionResult interact(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack);

    static void bootstrap(){
        EMPTY.put(Items.WATER_BUCKET, FILL_WATER);
        WATER.put(Items.WATER_BUCKET, FILL_WATER);
        WATER.put(Items.BUCKET, EMPTY_WATER);
    }

    static InteractionResult fillBucket(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack, ItemStack stack2, Predicate<BlockState> predicate, SoundEvent soundEvent) {
        if (!predicate.test(state)) {
            return InteractionResult.PASS;
        } else {
            if (!level.isClientSide) {
                Item item = stack.getItem();
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, stack2));
                //player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(item));
                level.setBlockAndUpdate(pos, MWBlocks.SLACK_TUB_BLOCK.get().setEmpty(state, level, pos));
                level.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    static InteractionResult emptyBucket(Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack, BlockState state, SoundEvent soundEvent) {
        if (!level.isClientSide) {
            Item item = stack.getItem();
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.BUCKET)));
            //player.awardStat(Stats.FILL_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(item));
            level.setBlockAndUpdate(pos, state);
            level.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
