package net.jmb19905.medievalworlds.common.multiblock;

import net.jmb19905.medievalworlds.core.registries.MWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.Optional;

@SuppressWarnings("ClassCanBeRecord")
public class ForgeShape {

    private final Level level;
    private final BlockPos pos;
    private final Direction direction;

    public static Optional<ForgeShape> findForgeShape(Level level, BlockPos pos) {
        ForgeShape shape = null;
        for(Direction dir : Direction.values()) {
            shape = new ForgeShape(level, pos, dir);
            if(!shape.isFormable()) shape = null;
            else break;
        }
        return Optional.ofNullable(shape);
    }

    protected ForgeShape(Level level, BlockPos pos, Direction direction) {
        this.level = level;
        this.pos = pos;
        this.direction = direction;
    }

    private BlockPos calculateForgeBlockPos(BlockPos center) {
        return center.below();
    }

    private BlockPos calculateBottomFrontMiddle(BlockPos center) {
        return calculateForgeBlockPos(center).relative(direction);
    }

    private BlockPos calculateBottomFrontLeft(BlockPos center) {
        return calculateBottomFrontMiddle(center).relative(direction.getClockWise());
    }

    private BlockPos calculateBottomFrontRight(BlockPos center) {
        return calculateBottomFrontMiddle(center).relative(direction.getClockWise().getOpposite());
    }

    private BlockPos calculateBottomMiddleLeft(BlockPos center) {
        return calculateForgeBlockPos(center).relative(direction.getClockWise());
    }

    private BlockPos calculateBottomMiddleRight(BlockPos center) {
        return calculateForgeBlockPos(center).relative(direction.getClockWise().getOpposite());
    }

    private BlockPos calculateBottomBack(BlockPos center) {
        return calculateForgeBlockPos(center).relative(direction.getOpposite());
    }

    private BlockPos calculateMiddleFrontLeft(BlockPos center) {
        return center.relative(direction).relative(direction.getClockWise());
    }

    private BlockPos calculateMiddleFrontRight(BlockPos center) {
        return center.relative(direction).relative(direction.getClockWise().getOpposite());
    }

    private BlockPos calculateMiddleMiddleLeft(BlockPos center) {
        return center.relative(direction.getClockWise());
    }

    private BlockPos calculateMiddleMiddleRight(BlockPos center) {
        return center.relative(direction.getClockWise().getOpposite());
    }

    private BlockPos calculateMiddleBack(BlockPos center) {
        return center.relative(direction.getOpposite());
    }

    private BlockPos calculateTop(BlockPos center) {
        return center.above();
    }

    private BlockPos calculateTopFront(BlockPos center) {
        return calculateTop(center).relative(direction);
    }

    private boolean isBrickStructureFormed(BlockPos center) {
        if(!isValidBrick(calculateBottomFrontMiddle(center))) return false;
        if(!isValidBrick(calculateBottomFrontLeft(center))) return false;
        if(!isValidBrick(calculateBottomFrontRight(center))) return false;
        if(!isValidBrick(calculateBottomMiddleLeft(center))) return false;
        if(!isValidBrick(calculateBottomMiddleRight(center))) return false;
        if(!isValidBrick(calculateBottomBack(center))) return false;
        if(!isValidBrick(calculateMiddleFrontLeft(center))) return false;
        if(!isValidBrick(calculateMiddleFrontRight(center))) return false;
        if(!isValidBrick(calculateMiddleMiddleLeft(center))) return false;
        if(!isValidBrick(calculateMiddleMiddleRight(center))) return false;
        if(!isValidBrick(calculateMiddleBack(center))) return false;
        if(!isValidBrick(calculateTop(center))) return false;
        return isValidBrick(calculateTopFront(center));
    }

    private boolean isValidBrick(BlockPos brickPos) {
        return level.getBlockState(brickPos).getBlock() == Blocks.BRICKS;
    }

    public boolean isFormable() {
        if(level.getBlockState(pos).getBlock() != Blocks.FIRE) return false;
        if(!isValidBrick(calculateForgeBlockPos(pos))) return false;
        return isBrickStructureFormed(pos);
    }

    public void form() {
        System.out.println("yay");
    }

    public boolean isFormed() {
        if(level.getBlockState(calculateForgeBlockPos(pos)).getBlock() != MWBlocks.FORGE_BLOCK.get()) return false;
        return isBrickStructureFormed(pos);
    }

}
