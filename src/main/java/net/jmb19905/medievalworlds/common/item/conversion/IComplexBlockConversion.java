package net.jmb19905.medievalworlds.common.item.conversion;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;

public interface IComplexBlockConversion {
    InteractionResult convert(UseOnContext ctx);
}
