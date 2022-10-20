package net.jmb19905.medievalworlds.common.block;

import net.jmb19905.medievalworlds.common.networking.NetworkStartupCommon;
import net.jmb19905.medievalworlds.common.networking.SteamEffectPacket;
import net.jmb19905.medievalworlds.common.recipes.slacktub.ISlackTubRecipe;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public class SlackTubBlock extends Block {

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(15, 1, 0, 16, 12, 16),
            Block.box(1, 1, 15, 15, 12, 16),
            Block.box(0, 1, 0, 1, 12, 16),
            Block.box(1, 1, 0, 15, 12, 1),
            Block.box(0, 0, 0, 16, 1, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape INSIDE = Block.box(1,1,1,14,11,14);

    public static final BooleanProperty FILLED = BooleanProperty.create("filled");
    public static final IntegerProperty EVAPORATION_CHANCE = IntegerProperty.create("evaporation_chance", 0, 10);

    public SlackTubBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FILLED, false).setValue(EVAPORATION_CHANCE, 0));
    }

    public static boolean isEmpty(BlockState state){
        return !state.getValue(FILLED);
    }

    public static BlockState setEmpty(BlockState state, Level level, BlockPos pos) {
        BlockState newState = state.setValue(FILLED, false).setValue(EVAPORATION_CHANCE, 0);
        level.setBlockAndUpdate(pos, newState);
        return newState;
    }

    public static BlockState setFull(BlockState state, Level level, BlockPos pos){
        BlockState newState = state.setValue(FILLED, true).setValue(EVAPORATION_CHANCE, 0);
        level.setBlockAndUpdate(pos, newState);
        return newState;
    }

    @Override
    public void entityInside(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, @Nonnull Entity entity) {
        if (!level.isClientSide && entity.isOnFire() && this.isEntityInsideContent(state, pos, entity)) {
            entity.clearFire();
            if (entity.mayInteract(level, pos)) {
                this.handleEntityOnFireInside(state, level, pos);
            }
        }
    }

    protected void handleEntityOnFireInside(BlockState state, Level level, BlockPos pos) {
        float evaporationFactor = SlackTubBlock.getEvaporationFactor(state) * .5f;
        boolean evaporates = level.getRandom().nextFloat() < evaporationFactor;
        if(evaporates) {
            level.setBlockAndUpdate(pos, state.setValue(SlackTubBlock.FILLED, false));
        }else {
            level.setBlockAndUpdate(pos, SlackTubBlock.increaseEvaporationChance(state));
        }
        //NetworkStartupCommon.simpleChannel.send(PacketDistributor.DIMENSION.with(level::dimension), new SteamEffectPacket(pos, 1));
        level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
    }

    protected boolean isEntityInsideContent(BlockState state, BlockPos pos, Entity entity) {
        return entity.getY() < (double)pos.getY() + this.getContentHeight(state) && entity.getBoundingBox().maxY > (double)pos.getY() + 0.25D;
    }

    protected double getContentHeight(BlockState state) {
        return (state.getValue(FILLED) ? 11 : 0) / 16.0D;
    }

    public static int getEvaporationChance(BlockState state) {
        return state.getValue(EVAPORATION_CHANCE);
    }

    public static float getEvaporationFactor(BlockState state) {
        return getEvaporationChance(state) / 10f;
    }

    public static BlockState increaseEvaporationChance(BlockState state) {
        int evaporationChance = getEvaporationChance(state);
        return state.setValue(EVAPORATION_CHANCE, evaporationChance < 10 ? evaporationChance + 1 : evaporationChance);
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        if(stack.getItem() == Items.WATER_BUCKET) {
            return emptyBucket(level, pos, player, hand, stack, state);
        }else if(stack.getItem() instanceof BucketItem bucketItem && bucketItem.getFluid() == Fluids.EMPTY) {
            return fillBucket(state, level, pos, player, hand, stack);
        }else {
            List<ISlackTubRecipe> recipes = level.getRecipeManager().getAllRecipesFor(MWRecipeSerializers.SLACK_TUB_TYPE);
            for(ISlackTubRecipe recipe : recipes) {
                if(recipe.getInput().test(stack)) {
                    return quenchItem(level, pos, player, hand, recipe, stack.getCount(), state);
                }
            }
        }
        return InteractionResult.FAIL;
    }

    protected InteractionResult fillBucket(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
        if (isEmpty(state)) {
            return InteractionResult.PASS;
        } else {
            if (!level.isClientSide) {
                Item item = stack.getItem();
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.WATER_BUCKET)));
                player.awardStat(Stats.ITEM_USED.get(item));
                level.setBlockAndUpdate(pos, SlackTubBlock.setEmpty(state, level, pos));
                level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    protected InteractionResult emptyBucket(Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack, BlockState state) {
        if (!level.isClientSide) {
            Item item = stack.getItem();
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.BUCKET)));
            player.awardStat(Stats.ITEM_USED.get(item));
            level.setBlockAndUpdate(pos, SlackTubBlock.setFull(state, level, pos));
            level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    protected InteractionResult quenchItem(Level level, BlockPos pos, Player player, InteractionHand hand, ISlackTubRecipe recipe, int count, BlockState state) {
        if(isEmpty(state)) {
            return InteractionResult.PASS;
        } else {
            if(!level.isClientSide) {
                float evaporationFactor = SlackTubBlock.getEvaporationFactor(state) * .1f;
                boolean evaporates = level.getRandom().nextFloat() < evaporationFactor;
                if(evaporates) {
                    level.setBlockAndUpdate(pos, state.setValue(SlackTubBlock.FILLED, false));
                }else {
                    level.setBlockAndUpdate(pos, SlackTubBlock.increaseEvaporationChance(state));
                }
                ItemStack stack = recipe.getResultItem().copy();
                stack.setCount(count);

                player.setItemInHand(hand, stack);
                NetworkStartupCommon.simpleChannel.send(PacketDistributor.DIMENSION.with(level::dimension), new SteamEffectPacket(pos, 1));
                level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    @Override
    public int getAnalogOutputSignal(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos) {
        return state.getValue(FILLED) ? 1 : 0;
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FILLED, EVAPORATION_CHANCE);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter blockGetter, @Nonnull BlockPos pos, @Nonnull CollisionContext ctx) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public VoxelShape getInteractionShape(@Nonnull BlockState p_60547_, @Nonnull BlockGetter p_60548_, @Nonnull BlockPos p_60549_) {
        return INSIDE;
    }

    @Override
    public boolean isPathfindable(@Nonnull BlockState p_60475_, @Nonnull BlockGetter p_60476_, @Nonnull BlockPos p_60477_, @Nonnull PathComputationType p_60478_) {
        return false;
    }
}