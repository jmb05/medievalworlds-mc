package net.jmb19905.medievalworlds.common.block.bloomery;

import net.jmb19905.medievalworlds.common.blockentities.bloomery.BloomeryTopBlockEntity;
import net.jmb19905.medievalworlds.core.registries.MWBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class BloomeryBlockTop extends AbstractBloomeryBlock{

    private static final VoxelShape TOP_SHAPE = Block.box(1, 0, 1, 15, 14, 15);

    public BloomeryBlockTop(Properties properties) {
        super(properties);
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

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> blockType) {
        return level.isClientSide ? null : blockType == MWBlockEntityTypes.BLOOMERY_TOP.get() ? BloomeryTopBlockEntity::tick : null;
    }

}
