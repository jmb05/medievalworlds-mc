package net.jmb19905.medievalworlds.common.item;

import net.jmb19905.medievalworlds.common.block.hearth.HearthShape;
import net.jmb19905.medievalworlds.common.item.conversion.*;
import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Blocks;

public class ForgeHammerItem extends BlockConverterItem {

    private static final IBlockConversionPredicate ANVIL_PREDICATE = (state, face) -> state.is(BlockTags.BASE_STONE_OVERWORLD);

    private static final ISimpleBlockConversion ANVIL_CONVERSION = () -> MWBlocks.STONE_ANVIL.get().defaultBlockState();

    private static final IBlockConversionPredicate HEARTH_PREDICATE = (state, face) -> state == Blocks.BRICKS.defaultBlockState() && face.get2DDataValue() != -1;

    private static final IComplexBlockConversion HEARTH_CONVERSION = ctx -> {
        HearthShape shape = HearthShape.findHearthShape(ctx.getLevel(), ctx.getClickedPos(), ctx.getClickedFace().getOpposite()).orElse(null);
        return (shape != null && shape.construct()) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    };

    public ForgeHammerItem(Tier tier, Properties properties) {
        super("forge_hammer", tier, properties);
        BlockConversionManager.registerConversion("forge_hammer", ANVIL_PREDICATE, ANVIL_CONVERSION);
        BlockConversionManager.registerConversion("forge_hammer", HEARTH_PREDICATE, HEARTH_CONVERSION);
    }
}