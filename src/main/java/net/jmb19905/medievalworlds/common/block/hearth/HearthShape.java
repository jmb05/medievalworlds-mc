package net.jmb19905.medievalworlds.common.block.hearth;

import com.google.common.collect.ImmutableList;
import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public class HearthShape {

    private final LevelAccessor levelAccessor;
    private final BlockPos pos;
    private final Direction facing;

    public static HearthShape createPhantomHearthShape(LevelAccessor levelAccessor, BlockPos pos, Direction facing) {
        return new HearthShape(levelAccessor, pos, facing);
    }

    public static Optional<HearthShape> findHearthShape(LevelAccessor levelAccessor, BlockPos pos, Direction facing) {
        return Optional.of(createPhantomHearthShape(levelAccessor, pos, facing)).filter(HearthShape::isValid);
    }

    public static Optional<HearthShape> findConstructedHearthShape(LevelAccessor levelAccessor, BlockPos pos, Direction facing) {
        return Optional.of(createPhantomHearthShape(levelAccessor, pos, facing)).filter(HearthShape::isConstructed);
    }

    private final List<Definition> definitions;

    private HearthShape(LevelAccessor levelAccessor, BlockPos pos, Direction facing) {
        this.levelAccessor = levelAccessor;
        this.pos = pos;
        this.facing = facing;
        BlockState brickState = Blocks.BRICKS.defaultBlockState();
        BlockState brickSlabState = Blocks.BRICK_SLAB.defaultBlockState();
        Definition main = new Definition(pos, brickState, HearthPartProperty.MAIN);
        Definition hood = new Definition(pos.above(), brickSlabState, HearthPartProperty.HOOD);
        Definition frontLeft = new Definition(pos.relative(facing.getCounterClockWise()), brickState, HearthPartProperty.FRONT_LEFT);
        Definition frontRight = new Definition(pos.relative(facing.getClockWise()), brickState, HearthPartProperty.FRONT_RIGHT);
        BlockPos backMiddlePos = pos.relative(facing);
        Definition backMiddle = new Definition(backMiddlePos, brickState, HearthPartProperty.BACK_MIDDLE);
        Definition backLeft = new Definition(backMiddlePos.relative(facing.getCounterClockWise()), brickState, HearthPartProperty.BACK_LEFT);
        Definition backRight = new Definition(backMiddlePos.relative(facing.getClockWise()), brickState, HearthPartProperty.BACK_RIGHT);
        BlockPos topMiddlePos = backMiddlePos.above();
        Definition topMiddle = new Definition(topMiddlePos, brickState, HearthPartProperty.TOP_MIDDLE);
        Definition topLeft = new Definition(topMiddlePos.relative(facing.getCounterClockWise()), brickState, HearthPartProperty.TOP_LEFT);
        Definition topRight = new Definition(topMiddlePos.relative(facing.getClockWise()), brickState, HearthPartProperty.TOP_RIGHT);
        definitions = ImmutableList.of(main, hood, frontLeft, frontRight, backMiddle, backLeft, backRight, topMiddle, topLeft, topRight);
    }

    public boolean isValid() {
        for (Definition def : definitions) {
            if (levelAccessor.getBlockState(def.pos) != def.normal) return false;
        }
        return true;
    }

    public boolean isConstructed() {
        for (Definition def : definitions) {
            BlockState state = levelAccessor.getBlockState(pos);
            if (!state.is(MWBlocks.HEARTH.get())) return false;
            if (state.getValue(HearthBlock.PART) != def.part || state.getValue(HearthBlock.FACING) != facing) return false;
        }
        return true;
    }

    public boolean construct() {
        if (!isValid()) return false;
        for (Definition definition : definitions) {
            BlockState newState = MWBlocks.HEARTH.get().defaultBlockState()
                    .setValue(HearthBlock.PART, definition.part)
                    .setValue(HearthBlock.FACING, facing);
            levelAccessor.setBlock(definition.pos, newState, Block.UPDATE_ALL);
        }
        return true;
    }

    public void deconstruct() {
        for (Definition definition : definitions) {
            if (levelAccessor.getBlockState(definition.pos).is(MWBlocks.HEARTH.get())) {
                levelAccessor.setBlock(definition.pos, definition.normal, Block.UPDATE_ALL);
            }
        }
    }

    private record Definition(BlockPos pos, BlockState normal, HearthPartProperty part) {}
}
