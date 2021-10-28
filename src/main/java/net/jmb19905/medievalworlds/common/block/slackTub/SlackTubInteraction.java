package net.jmb19905.medievalworlds.common.block.slackTub;

import net.jmb19905.medievalworlds.common.item.IAnvilItem;
import net.jmb19905.medievalworlds.common.networking.NetworkStartupCommon;
import net.jmb19905.medievalworlds.common.networking.SteamEffectPacket;
import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.jmb19905.medievalworlds.util.BlockInteraction;
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
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import java.util.Map;
import java.util.function.Predicate;

public interface SlackTubInteraction extends BlockInteraction {
    Map<Item, SlackTubInteraction> INTERACTIONS = BlockInteraction.newInteractionMap();

    SlackTubInteraction FILL_WATER = (state, level, pos, player, hand, stack)
            -> emptyBucket(level, pos, player, hand, stack, SlackTubBlock.setFull(state, level, pos), SoundEvents.BUCKET_EMPTY);

    SlackTubInteraction EMPTY_WATER = (state, level, pos, player, hand, stack)
            -> fillBucket(state, level, pos, player, hand, stack, new ItemStack(Items.WATER_BUCKET), SlackTubBlock::isFull, SoundEvents.BUCKET_FILL);

    SlackTubInteraction QUENCH = (state, level, pos, player, hand, stack)
            -> quenchItem(level, pos, player, hand, stack, state, SlackTubBlock::isFull, SoundEvents.FIRE_EXTINGUISH);

    static void bootstrap(){
        INTERACTIONS.put(Items.WATER_BUCKET, FILL_WATER);
        INTERACTIONS.put(Items.BUCKET, EMPTY_WATER);
        INTERACTIONS.put(MWItems.HEATED_IRON_INGOT_ITEM.get(), QUENCH);
        INTERACTIONS.put(MWItems.HEATED_STEEL_INGOT_ITEM.get(), QUENCH);
        INTERACTIONS.put(MWItems.HEATED_COPPER_INGOT_ITEM.get(), QUENCH);
        INTERACTIONS.put(MWItems.HEATED_TIN_INGOT_ITEM.get(), QUENCH);
        INTERACTIONS.put(MWItems.HEATED_BRONZE_INGOT_ITEM.get(), QUENCH);
        INTERACTIONS.put(MWItems.HEATED_SILVER_INGOT_ITEM.get(), QUENCH);
        INTERACTIONS.put(MWItems.HEATED_GOLD_INGOT_ITEM.get(), QUENCH);
        INTERACTIONS.put(MWItems.HEATED_NETHERITE_INGOT_ITEM.get(), QUENCH);
        INTERACTIONS.put(MWItems.HEATED_IRON_PLATE_ITEM.get(), QUENCH);
        INTERACTIONS.put(MWItems.HEATED_STEEL_PLATE_ITEM.get(), QUENCH);
        INTERACTIONS.put(MWItems.HEATED_COPPER_PLATE_ITEM.get(), QUENCH);
        INTERACTIONS.put(MWItems.HEATED_TIN_PLATE_ITEM.get(), QUENCH);
        INTERACTIONS.put(MWItems.HEATED_BRONZE_PLATE_ITEM.get(), QUENCH);
        INTERACTIONS.put(MWItems.HEATED_SILVER_PLATE_ITEM.get(), QUENCH);
        INTERACTIONS.put(MWItems.HEATED_GOLD_PLATE_ITEM.get(), QUENCH);
        INTERACTIONS.put(MWItems.HEATED_NETHERITE_PLATE_ITEM.get(), QUENCH);
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
                level.setBlockAndUpdate(pos, SlackTubBlock.setEmpty(state, level, pos));
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

    static InteractionResult quenchItem(Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack, BlockState state, Predicate<BlockState> condition, SoundEvent soundEvent) {
        if(!condition.test(state)) {
            return InteractionResult.PASS;
        } else {
            if(!level.isClientSide) {
                float evaporationFactor = SlackTubBlock.getEvaporationFactor(state) * .5f;
                boolean evaporates = level.getRandom().nextFloat() < evaporationFactor;
                if(evaporates) {
                    level.setBlockAndUpdate(pos, state.setValue(SlackTubBlock.FILLED, false));
                }else {
                    level.setBlockAndUpdate(pos, SlackTubBlock.increaseEvaporationChance(state));
                }
                ItemStack newStack = new ItemStack(((IAnvilItem) stack.getItem()).getBaseItem(), stack.getCount());
                player.setItemInHand(hand, newStack);
                sendStemEffectPacket(level, pos, 1);
                level.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    static void sendStemEffectPacket(Level level, BlockPos position, float spread) {
        NetworkStartupCommon.simpleChannel.send(PacketDistributor.DIMENSION.with(level::dimension), new SteamEffectPacket(position, spread));
    }

}
