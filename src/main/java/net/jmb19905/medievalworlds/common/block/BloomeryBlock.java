package net.jmb19905.medievalworlds.common.block;

import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.jmb19905.medievalworlds.common.registries.MWBlockEntityTypes;
import net.jmb19905.medievalworlds.common.blockentities.BloomeryBlockEntity;
import net.jmb19905.medievalworlds.util.CustomItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public abstract class BloomeryBlock extends Block implements EntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty CLAY = BooleanProperty.create("clay");

    public BloomeryBlock(Properties properties) {
        super(properties);
    }

    public static class Bottom extends BloomeryBlock{

        public Bottom(Properties properties) {
            super(properties);
            this.registerDefaultState(this.getStateDefinition().any()
                    .setValue(FACING, Direction.NORTH)
                    .setValue(CLAY, true));
        }

        @Nonnull
        @Override
        public VoxelShape getShape(@Nonnull BlockState p_60555_, @Nonnull BlockGetter p_60556_, @Nonnull BlockPos p_60557_, @Nonnull CollisionContext p_60558_) {
            return BOTTOM_SHAPE;
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
            return MWBlockEntityTypes.BLOOMERY_BOTTOM.get().create(pos, state);
        }
    }

    public static class Top extends BloomeryBlock{

        public Top(Properties properties) {
            super(properties);
            this.registerDefaultState(this.getStateDefinition().any()
                    .setValue(FACING, Direction.NORTH)
                    .setValue(CLAY, true));
        }

        @Nonnull
        @Override
        public VoxelShape getShape(@Nonnull BlockState p_60555_, @Nonnull BlockGetter p_60556_, @Nonnull BlockPos p_60557_, @Nonnull CollisionContext p_60558_) {
            return TOP_SHAPE;
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
            return MWBlockEntityTypes.BLOOMERY_TOP.get().create(pos, state);
        }
    }

    /*@Nonnull
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Nonnull
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }*/

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, CLAY);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        //return state.get(LIT) ? super.getLightValue(state, world, pos) : 0;
        return 0;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable LivingEntity placer, @Nonnull ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if(stack.hasCustomHoverName()){
            BlockEntity entity = level.getBlockEntity(pos);
            if(entity instanceof BloomeryBlockEntity.Bottom) {
                ((BloomeryBlockEntity.Bottom) entity).setCustomName(stack.getDisplayName());
            }
        }
        BlockPos topPosition = pos.above();
        level.setBlockAndUpdate(topPosition, MWBlocks.BLOOMERY_TOP_BLOCK.get().defaultBlockState());
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        if(!level.isClientSide) {
            BlockEntity entity = level.getBlockEntity(pos);
            if(entity instanceof BloomeryBlockEntity.Bottom) {
                NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) entity, pos);
                return InteractionResult.SUCCESS;
            }else if(entity instanceof BloomeryBlockEntity.Top) {
                BlockEntity bottomEntity = level.getBlockEntity(pos.below());
                NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) bottomEntity, pos.below());
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        BlockEntity entity = level.getBlockEntity(pos);
        if(entity instanceof BloomeryBlockEntity.Bottom bloomeryBlockEntity && state.getBlock() != newState.getBlock()) {
            ((CustomItemHandler) bloomeryBlockEntity.getInventory()).toNonNullList().forEach(stack -> {
                ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
                level.addFreshEntity(itemEntity);
            });
        }

        if(state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
            level.removeBlockEntity(pos);
        }
    }

    private static final VoxelShape BOTTOM_SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    private static final VoxelShape TOP_SHAPE = Block.box(1, 0, 1, 15, 14, 15);

}
