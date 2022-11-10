package net.jmb19905.medievalworlds.common.item.conversion;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

public class BlockConverterItem extends TieredItem {

    public BlockConverterItem(String converterName, Tier tier, Properties properties) {
        super(tier, properties);
        BlockConversionManager.registerConverter(this, converterName);//TODO: bad passing not fully initialized object
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext ctx) {
        return BlockConversionManager.processConversions(this, ctx);
    }
}
