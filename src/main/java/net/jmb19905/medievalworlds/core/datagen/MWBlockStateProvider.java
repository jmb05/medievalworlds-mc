package net.jmb19905.medievalworlds.core.datagen;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.core.registries.MWBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MWBlockStateProvider extends BlockStateProvider {

    public MWBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, MedievalWorlds.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(MWBlocks.BRONZE_BLOCK.get());
        simpleBlock(MWBlocks.CHARCOAl_PLANKS.get());
        simpleBlock(MWBlocks.DEEPSLATE_RUBY_ORE_BLOCK.get());
        simpleBlock(MWBlocks.DEEPSLATE_SILVER_ORE_BLOCK.get());
        simpleBlock(MWBlocks.DEEPSLATE_TIN_ORE_BLOCK.get());
        simpleBlock(MWBlocks.LIMESTONE_BLOCK.get());
        simpleBlock(MWBlocks.LIMESTONE_BRICKS_BLOCK.get());
        simpleBlock(MWBlocks.RAW_SILVER_BLOCK.get());
        simpleBlock(MWBlocks.RAW_TIN_BLOCK.get());
        simpleBlock(MWBlocks.RUBY_BLOCK.get());
        simpleBlock(MWBlocks.RUBY_ORE_BLOCK.get());
        simpleBlock(MWBlocks.SILVER_BLOCK.get());
        simpleBlock(MWBlocks.SILVER_ORE_BLOCK.get());
        simpleBlock(MWBlocks.STEEL_BLOCK.get());
        simpleBlock(MWBlocks.TIN_BLOCK.get());
        simpleBlock(MWBlocks.TIN_ORE_BLOCK.get());
        axisBlock(MWBlocks.CHARCOAL_LOG.get());
    }

}
