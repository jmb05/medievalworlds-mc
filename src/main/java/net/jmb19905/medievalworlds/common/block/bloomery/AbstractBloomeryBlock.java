package net.jmb19905.medievalworlds.common.block.bloomery;

import net.jmb19905.medievalworlds.common.blockentities.bloomery.BloomeryBottomBlockEntity;
import net.jmb19905.medievalworlds.common.blockentities.bloomery.BloomeryTopBlockEntity;
import net.jmb19905.medievalworlds.core.registries.MWBlocks;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public abstract class AbstractBloomeryBlock extends Block implements EntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty CLAY = BooleanProperty.create("clay");
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public AbstractBloomeryBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(CLAY, true).setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, CLAY, LIT);
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
            if(entity instanceof BloomeryBottomBlockEntity) {
                ((BloomeryBottomBlockEntity) entity).setCustomName(stack.getDisplayName());
            }
        }
        BlockPos topPosition = pos.above();
        level.setBlockAndUpdate(topPosition, MWBlocks.BLOOMERY_CLAY_TOP_BLOCK.get().defaultBlockState());
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        if(!level.isClientSide) {
            BlockEntity entity = level.getBlockEntity(pos);
            if(entity instanceof BloomeryBottomBlockEntity) {
                NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) entity, pos);
                return InteractionResult.SUCCESS;
            }else if(entity instanceof BloomeryTopBlockEntity) {
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
        if(entity instanceof BloomeryBottomBlockEntity bloomeryBlockEntity && state.getBlock() != newState.getBlock()) {
            ((CustomItemHandler) bloomeryBlockEntity.getInventory()).toNonNullList().forEach(stack -> {
                ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
                level.addFreshEntity(itemEntity);
            });
        }

        if(state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
            level.removeBlockEntity(pos);
        }
    }
}
