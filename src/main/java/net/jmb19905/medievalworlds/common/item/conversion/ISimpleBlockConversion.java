package net.jmb19905.medievalworlds.common.item.conversion;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;

public interface ISimpleBlockConversion extends IComplexBlockConversion {
    @Override
    default InteractionResult convert(UseOnContext ctx) {
        ctx.getLevel().setBlock(ctx.getClickedPos(), getBlockState(), 3);
        return InteractionResult.SUCCESS;
    }

    BlockState getBlockState();
}
