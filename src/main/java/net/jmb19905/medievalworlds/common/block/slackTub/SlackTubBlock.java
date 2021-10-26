package net.jmb19905.medievalworlds.common.block.slackTub;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import java.util.Map;
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

    private Map<Item, SlackTubInteraction> interactions;

    public SlackTubBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FILLED, false));
        this.interactions = SlackTubInteraction.EMPTY;
    }

    public boolean isFull(BlockState state){
        return state.getValue(FILLED);
    }

    public BlockState setEmpty(BlockState state, Level level, BlockPos pos) {
        BlockState newState = state.setValue(FILLED, false);
        level.setBlockAndUpdate(pos, newState);
        this.interactions = SlackTubInteraction.EMPTY;
        return newState;
    }

    public BlockState setFull(BlockState state, Level level, BlockPos pos){
        BlockState newState = state.setValue(FILLED, true);
        level.setBlockAndUpdate(pos, newState);
        this.interactions = SlackTubInteraction.WATER;
        return newState;
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        SlackTubInteraction interaction = this.interactions.get(stack.getItem());
        return interaction.interact(state, level, pos, player, hand, stack);
    }

    @Override
    public int getAnalogOutputSignal(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos) {
        return state.getValue(FILLED) ? 1 : 0;
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FILLED);
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
