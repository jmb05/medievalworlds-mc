package net.jmb19905.medievalworlds.common.datagen;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class MWBlockStateProvider extends BlockStateProvider {

    public MWBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, MedievalWorlds.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        logBlockAndItem(MWBlocks.CHARCOAL_LOG.get());
        simpleBlockAndItem(MWBlocks.CHARCOAL_PLANKS.get());
        customSimple(MWBlocks.STONE_ANVIL.get());
        customHorizontal(MWBlocks.STEEL_ANVIL.get());

        simpleBlockAndItem(MWBlocks.STEEL_BLOCK.get());
        simpleBlockAndItem(MWBlocks.SILVER_BLOCK.get());
        simpleBlockAndItem(MWBlocks.BRONZE_BLOCK.get());
        simpleBlockAndItem(MWBlocks.TIN_BLOCK.get());

        simpleBlockAndItem(MWBlocks.SILVER_ORE.get());
        simpleBlockAndItem(MWBlocks.TIN_ORE.get());

        simpleBlockAndItem(MWBlocks.DEEPSLATE_SILVER_ORE.get());
        simpleBlockAndItem(MWBlocks.DEEPSLATE_TIN_ORE.get());

        simpleBlockAndItem(MWBlocks.RAW_SILVER_BLOCK.get());
        simpleBlockAndItem(MWBlocks.RAW_TIN_BLOCK.get());
    }

    private void customSimple(Block block) {
        ModelFile modelFile = models().getExistingFile(new ResourceLocation(MedievalWorlds.MOD_ID, "block/" + name(block)));
        super.simpleBlock(block, modelFile);
        super.simpleBlockItem(block, modelFile);
    }

    private void customHorizontal(Block block) {
        ModelFile modelFile = models().getExistingFile(new ResourceLocation(MedievalWorlds.MOD_ID, "block/" + name(block)));
        super.horizontalBlock(block, modelFile);
        super.simpleBlockItem(block, modelFile);
    }

    private void logBlockAndItem(RotatedPillarBlock block) {
        ModelFile modelFile = models().cubeColumn(
                name(block),
                new ResourceLocation(MedievalWorlds.MOD_ID, "block/" + name(block) + "_side"),
                new ResourceLocation(MedievalWorlds.MOD_ID, "block/" + name(block) + "_end")
        );
        ModelFile modelFileHorizontal = models().cubeColumnHorizontal(
                name(block),
                new ResourceLocation(MedievalWorlds.MOD_ID, "block/" + name(block) + "_side"),
                new ResourceLocation(MedievalWorlds.MOD_ID, "block/" + name(block) + "_end")
        );
        super.axisBlock(block, modelFile, modelFileHorizontal);
        super.simpleBlockItem(block, modelFile);
    }

    private void simpleBlockAndItem(Block block) {
        ModelFile modelFile = cubeAll(block);
        super.simpleBlock(block, modelFile);
        super.simpleBlockItem(block, modelFile);
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    public ResourceLocation blockTexture(Block block) {
        ResourceLocation name = key(block);
        return new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath());
    }

}
