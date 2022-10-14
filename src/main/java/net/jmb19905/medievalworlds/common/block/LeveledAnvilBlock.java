package net.jmb19905.medievalworlds.common.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

public class LeveledAnvilBlock extends CustomAnvilBlock{

    public static final IntegerProperty ANVIL_LEVEL = IntegerProperty.create("anvil_level", 0, 2);

    public LeveledAnvilBlock(BlockState damaged, float damageFactor, int anvilLevel, Properties properties) {
        super(damaged, damageFactor, properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(AnvilBlock.FACING, Direction.NORTH).setValue(ANVIL_LEVEL, anvilLevel));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ANVIL_LEVEL);
    }
}
