package net.jmb19905.medievalworlds.common.blockentities.bloomery;

import net.jmb19905.medievalworlds.core.registries.MWBlockEntityTypes;
import net.jmb19905.medievalworlds.core.registries.MWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BloomeryTopBlockEntity extends AbstractBloomeryBlockEntity {

    public BloomeryTopBlockEntity(BlockPos pos, BlockState state) {
        super(MWBlockEntityTypes.BLOOMERY_TOP.get(), pos, state);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        BlockPos bottomPos = pos.below();
        assert level != null;
        if(level.getBlockState(bottomPos).getBlock() != MWBlocks.BLOOMERY_CLAY_BOTTOM_BLOCK.get()){
            level.destroyBlock(pos, false);
        }
    }
}
