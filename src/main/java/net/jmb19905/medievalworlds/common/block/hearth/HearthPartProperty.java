package net.jmb19905.medievalworlds.common.block.hearth;

import net.jmb19905.medievalworlds.common.block.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public enum HearthPartProperty implements StringRepresentable {

    MAIN("main", null, (pos, dir) -> pos, createMainShape()),
    HOOD("hood", MAIN, (pos, dir) -> pos.below(), createHoodShape()),
    FRONT_LEFT("front_left", MAIN, (pos, dir) -> pos.relative(dir.getClockWise()), createFrontLeftShape()),
    FRONT_RIGHT("front_right", MAIN, (pos, dir) -> pos.relative(dir.getCounterClockWise()), createFrontRightShape()),
    BACK_MIDDLE("back_middle", MAIN, (pos, dir) -> pos.relative(dir.getOpposite()), createBackMiddleShape()),
    BACK_LEFT("back_left", BACK_MIDDLE, (pos, dir) -> pos.relative(dir.getClockWise()), Shapes.block()),
    BACK_RIGHT("back_right", BACK_MIDDLE, (pos, dir) -> pos.relative(dir.getCounterClockWise()), Shapes.block()),
    TOP_MIDDLE("top_middle", BACK_MIDDLE, (pos, dir) -> pos.below(), createTopMiddleShape()),
    TOP_LEFT("top_left", TOP_MIDDLE, (pos, dir) -> pos.relative(dir.getClockWise()), Shapes.block()),
    TOP_RIGHT("top_right", TOP_MIDDLE, (pos, dir) -> pos.relative(dir.getCounterClockWise()), Shapes.block());


    private static VoxelShape createMainShape() {
        return Shapes.or(
                createBottomShape(),
                Block.box(0, 14, 8, 2, 16, 16),
                Block.box(14, 14, 8, 16, 16, 16));
    }

    private static VoxelShape createFrontLeftShape() {
        return Block.box(0, 0, 0, 8, 14, 16);
    }

    private static VoxelShape createFrontRightShape() {
        return Block.box(8, 0, 0, 16, 14, 16);
    }

    private static VoxelShape createBackMiddleShape() {
        return Shapes.or(
            createBottomShape(),
            Block.box(0,14,0, 4, 16, 16),
            Block.box(12,14,0, 16, 16, 16),
            Block.box(4, 14, 15, 12, 16, 16));
    }

    private static VoxelShape createHoodShape(){
        return Shapes.or(
                Shapes.box(0.273208125, 0.2840225, 0.5, 0.726958125, 0.4090225, 1),
                Shapes.box(0.0375, 0, 0.5, 0.1625, 0.0625, 1),
                Shapes.box(0.0625, 0.0625, 0.5, 0.1875, 0.16875, 1),
                Shapes.box(0.15625, 0.2, 0.5, 0.28125, 0.325, 1),
                Shapes.box(0.21875, 0.2625, 0.5, 0.34375, 0.3875, 1),
                Shapes.box(0.84375, 0, 0.5, 0.96875, 0.0625, 1),
                Shapes.box(0.8125, 0.0625, 0.5, 0.9375, 0.16875, 1),
                Shapes.box(0.71875, 0.2, 0.5, 0.84375, 0.325, 1),
                Shapes.box(0.65625, 0.2625, 0.5, 0.78125, 0.3875, 1),
                Shapes.box(0.7625, 0.16875, 0.5, 0.8875, 0.23125, 1),
                Shapes.box(0.1125, 0.16875, 0.5, 0.2375, 0.23125, 1));
    }

    private static VoxelShape createTopMiddleShape(){
        return Shapes.or(
            Shapes.box(0.75, 0, 0, 1, 1, 1),
            Shapes.box(0.25, 0.3125, 0, 0.75, 1, 0.1875),
            Shapes.box(0.25, 0.875, 0.1875, 0.75, 1, 0.375),
            Shapes.box(0.25, 0, 0.8125, 0.75, 1, 1),
            Shapes.box(0, 0, 0, 0.25, 1, 1),
            Shapes.box(0.25, 0.375, 0.0625, 0.75, 0.375, 0.8125));
    }

    private final String name;
    private final HearthPartProperty parent;
    private final BiFunction<BlockPos, Direction, BlockPos> toParent;
    private final VoxelShape northShape;
    private final VoxelShape southShape;
    private final VoxelShape westShape;
    private final VoxelShape eastShape;

    HearthPartProperty(String name, HearthPartProperty parent, BiFunction<BlockPos, Direction, BlockPos> toParent, VoxelShape southShape) {
        this.name = name;
        this.parent = parent;
        this.toParent = toParent;
        this.northShape = BlockHelper.rotateVoxelShape(Direction.SOUTH, Direction.NORTH, southShape);
        this.southShape = southShape;
        this.westShape = BlockHelper.rotateVoxelShape(Direction.SOUTH, Direction.WEST, southShape);
        this.eastShape = BlockHelper.rotateVoxelShape(Direction.SOUTH, Direction.EAST, southShape);
    }

    public VoxelShape getShape(Direction facing) {
        switch (facing) {
            case SOUTH -> {
                return southShape;
            }
            case WEST -> {
                return westShape;
            }
            case EAST -> {
                return eastShape;
            }
        }
        return northShape;
    }

    public BlockPos getMainPosition(BlockPos pos, Direction facing) {
        HearthPartProperty part = this;
        BlockPos currentPos = pos;
        while (part.parent != null) {
            currentPos = part.getParentPosition(currentPos, facing);
            part = part.parent;
        }
        return currentPos;
    }

    public BlockPos getParentPosition(BlockPos pos, Direction facing) {
        return toParent.apply(pos, facing);
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }

    private static VoxelShape createBottomShape() {
        return Block.box(0, 0, 0, 16, 14, 16);
    }
}