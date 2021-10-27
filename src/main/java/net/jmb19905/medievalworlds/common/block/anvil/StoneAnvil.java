package net.jmb19905.medievalworlds.common.block.anvil;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

public class StoneAnvil extends CustomAnvilBlock{

    private static final VoxelShape SHAPE = Stream.of(Block.box(0, 0, 0.0625, 1, 1, 0.9375),
            Block.box(0.0625, 0, 0.9375, 0.9375, 1, 1),
            Block.box(0.0625, 0, 0, 0.9375, 1, 0.0625)).reduce((s1, s2) -> Shapes.join(s1, s2, BooleanOp.OR)).get();

    public StoneAnvil(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter getter, @Nonnull BlockPos pos, @Nonnull CollisionContext ctx) {
        return SHAPE;
    }
}
