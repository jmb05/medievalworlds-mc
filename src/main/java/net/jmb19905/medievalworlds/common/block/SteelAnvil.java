package net.jmb19905.medievalworlds.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;

public class SteelAnvil extends LeveledAnvilBlock {

    private static final VoxelShape SHAPE_E = createShapeNX();
    private static final VoxelShape SHAPE_S = createShapePZ();
    private static final VoxelShape SHAPE_W = createShapePX();
    private static final VoxelShape SHAPE_N = createShapeNZ();

    public SteelAnvil() {
        this(null, 0.0f, 2, BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.ANVIL));
    }

    public SteelAnvil(BlockState damaged, float damageFactor, int anvilLevel, Properties properties) {
        super(damaged, damageFactor, anvilLevel, properties);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter getter, @Nonnull BlockPos pos, @Nonnull CollisionContext ctx) {
        switch (state.getValue(AnvilBlock.FACING)) {
            case NORTH -> {
                return SHAPE_N;
            }
            case EAST -> {
                return SHAPE_E;
            }
            case SOUTH -> {
                return SHAPE_S;
            }
            case WEST -> {
                return SHAPE_W;
            }
        }
        return SHAPE_N;
    }

    private static VoxelShape createShapeNZ(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.125, 0, 0.125, 0.875, 0.25, 0.875));
        shape = Shapes.or(shape, Shapes.box(0.375, 0.375, 0.25, 0.625, 0.625, 0.75));
        shape = Shapes.or(shape, Shapes.box(0.1875, 0.625, 0.25, 0.8125, 1, 0.75));
        shape = Shapes.or(shape, Shapes.box(0.25, 0.625, 0.125, 0.75, 0.9375, 0.1875));
        shape = Shapes.or(shape, Shapes.box(0.3125, 0.625, 0, 0.6875, 0.9375, 0.125));
        shape = Shapes.or(shape, Shapes.box(0.25, 0.625, 0.1875, 0.75, 0.96875, 0.25));
        shape = Shapes.or(shape, Shapes.box(0.375, 0.6875, -0.125, 0.625, 0.9375, 0));
        shape = Shapes.or(shape, Shapes.box(0.4375, 0.75, -0.25, 0.5625, 0.9375, -0.125));
        shape = Shapes.or(shape, Shapes.box(0.25, 0.25, 0.1875, 0.75, 0.3125, 0.8125));
        shape = Shapes.or(shape, Shapes.box(0.25, 0.3125, 0.1875, 0.75, 0.375, 0.8125));
        shape = Shapes.or(shape, Shapes.box(0.1875, 0.75, 0.875, 0.8125, 1, 1));
        shape = Shapes.or(shape, Shapes.box(0.1875, 0.6875, 0.75, 0.8125, 1, 0.875));

        return shape;
    }

    public static VoxelShape createShapeNX(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.125, 0, 0.125, 0.875, 0.25, 0.875));
        shape = Shapes.or(shape, Shapes.box(0.4375, 0.25, 0.25, 0.8125, 0.3125, 0.75));
        shape = Shapes.or(shape, Shapes.box(0.1875, 0.3125, 0.25, 0.8125, 0.375, 0.75));
        shape = Shapes.or(shape, Shapes.box(0.1875, 0.25, 0.25, 0.4375, 0.3125, 0.75));
        shape = Shapes.or(shape, Shapes.box(0.25, 0.375, 0.375, 0.75, 0.625, 0.625));
        shape = Shapes.or(shape, Shapes.box(0.25, 0.625, 0.1875, 0.75, 1, 0.8125));
        shape = Shapes.or(shape, Shapes.box(0, 0.75, 0.1875, 0.125, 1, 0.8125));
        shape = Shapes.or(shape, Shapes.box(0.125, 0.6875, 0.1875, 0.25, 1, 0.8125));
        shape = Shapes.or(shape, Shapes.box(0.8125, 0.625, 0.25, 0.875, 0.9375, 0.75));
        shape = Shapes.or(shape, Shapes.box(0.75, 0.625, 0.25, 0.8125, 0.96875, 0.75));
        shape = Shapes.or(shape, Shapes.box(0.875, 0.625, 0.3125, 1, 0.9375, 0.6875));
        shape = Shapes.or(shape, Shapes.box(1, 0.6875, 0.375, 1.125, 0.9375, 0.625));
        shape = Shapes.or(shape, Shapes.box(1.125, 0.75, 0.4375, 1.25, 0.9375, 0.5625));

        return shape;
    }

    public static VoxelShape createShapePZ(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.125, 0, 0.125, 0.875, 0.25, 0.875));
        shape = Shapes.or(shape, Shapes.box(0.25, 0.25, 0.4375, 0.75, 0.3125, 0.8125));
        shape = Shapes.or(shape, Shapes.box(0.25, 0.3125, 0.1875, 0.75, 0.375, 0.8125));
        shape = Shapes.or(shape, Shapes.box(0.25, 0.25, 0.1875, 0.75, 0.3125, 0.4375));
        shape = Shapes.or(shape, Shapes.box(0.375, 0.375, 0.25, 0.625, 0.625, 0.75));
        shape = Shapes.or(shape, Shapes.box(0.1875, 0.625, 0.25, 0.8125, 1, 0.75));
        shape = Shapes.or(shape, Shapes.box(0.1875, 0.75, 0, 0.8125, 1, 0.125));
        shape = Shapes.or(shape, Shapes.box(0.1875, 0.6875, 0.125, 0.8125, 1, 0.25));
        shape = Shapes.or(shape, Shapes.box(0.25, 0.625, 0.8125, 0.75, 0.9375, 0.875));
        shape = Shapes.or(shape, Shapes.box(0.25, 0.625, 0.75, 0.75, 0.96875, 0.8125));
        shape = Shapes.or(shape, Shapes.box(0.3125, 0.625, 0.875, 0.6875, 0.9375, 1));
        shape = Shapes.or(shape, Shapes.box(0.375, 0.6875, 1, 0.625, 0.9375, 1.125));
        shape = Shapes.or(shape, Shapes.box(0.4375, 0.75, 1.125, 0.5625, 0.9375, 1.25));

        return shape;
    }

    public static VoxelShape createShapePX(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.125, 0, 0.125, 0.875, 0.25, 0.875));
        shape = Shapes.or(shape, Shapes.box(0.1875, 0.25, 0.25, 0.5625, 0.3125, 0.75));
        shape = Shapes.or(shape, Shapes.box(0.1875, 0.3125, 0.25, 0.8125, 0.375, 0.75));
        shape = Shapes.or(shape, Shapes.box(0.5625, 0.25, 0.25, 0.8125, 0.3125, 0.75));
        shape = Shapes.or(shape, Shapes.box(0.25, 0.375, 0.375, 0.75, 0.625, 0.625));
        shape = Shapes.or(shape, Shapes.box(0.25, 0.625, 0.1875, 0.75, 1, 0.8125));
        shape = Shapes.or(shape, Shapes.box(0.875, 0.75, 0.1875, 1, 1, 0.8125));
        shape = Shapes.or(shape, Shapes.box(0.75, 0.6875, 0.1875, 0.875, 1, 0.8125));
        shape = Shapes.or(shape, Shapes.box(0.125, 0.625, 0.25, 0.1875, 0.9375, 0.75));
        shape = Shapes.or(shape, Shapes.box(0.1875, 0.625, 0.25, 0.25, 0.96875, 0.75));
        shape = Shapes.or(shape, Shapes.box(0, 0.625, 0.3125, 0.125, 0.9375, 0.6875));
        shape = Shapes.or(shape, Shapes.box(-0.125, 0.6875, 0.375, 0, 0.9375, 0.625));
        shape = Shapes.or(shape, Shapes.box(-0.25, 0.75, 0.4375, -0.125, 0.9375, 0.5625));

        return shape;
    }
}
