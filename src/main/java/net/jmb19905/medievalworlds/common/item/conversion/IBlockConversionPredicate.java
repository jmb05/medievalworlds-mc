package net.jmb19905.medievalworlds.common.item.conversion;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public interface IBlockConversionPredicate {
    boolean check(BlockState state, Direction face);
}
