package net.jmb19905.medievalworlds.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;

public class StoneAnvil extends LeveledAnvilBlock {

    private static final VoxelShape SHAPE = createShape();

    public StoneAnvil() {
        this(null, .03f, 0, BlockBehaviour.Properties.of(Material.STONE).strength(3.0f).sound(SoundType.STONE));
    }

    public StoneAnvil(BlockState damaged, float damageFactor, int anvilLevel, Properties properties) {
        super(damaged, damageFactor, anvilLevel, properties);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter getter, @Nonnull BlockPos pos, @Nonnull CollisionContext ctx) {
        return SHAPE;
    }

    private static VoxelShape createShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0, 0, 0.0625, 1, 1, 0.9375));
        shape = Shapes.or(shape, Shapes.box(0.0625, 0, 0.9375, 0.9375, 1, 1));
        shape = Shapes.or(shape, Shapes.box(0.0625, 0, 0, 0.9375, 1, 0.0625));

        return shape;
    }
}
