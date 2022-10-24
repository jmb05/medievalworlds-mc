package net.jmb19905.medievalworlds.common.datagen;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.jmb19905.medievalworlds.common.registries.MWTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MWBlockTagsProvider extends BlockTagsProvider {

    public MWBlockTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MedievalWorlds.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags() {
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(MWBlocks.CHARCOAL_LOG.get())
                .add(MWBlocks.CHARCOAL_PLANKS.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(
                        MWBlocks.STEEL_ANVIL.get(),
                        MWBlocks.STEEL_BLOCK.get(),
                        MWBlocks.SILVER_BLOCK.get(),
                        MWBlocks.BRONZE_BLOCK.get(),
                        MWBlocks.TIN_BLOCK.get(),
                        MWBlocks.SILVER_ORE.get(),
                        MWBlocks.TIN_ORE.get(),
                        MWBlocks.DEEPSLATE_SILVER_ORE.get(),
                        MWBlocks.DEEPSLATE_TIN_ORE.get(),
                        MWBlocks.RAW_SILVER_BLOCK.get(),
                        MWBlocks.RAW_TIN_BLOCK.get()
                );

        tag(MWTags.Blocks.ORES_SILVER)
                .add(MWBlocks.SILVER_ORE.get())
                .add(MWBlocks.DEEPSLATE_SILVER_ORE.get());

        tag(MWTags.Blocks.ORES_TIN)
                .add(MWBlocks.TIN_ORE.get(), MWBlocks.DEEPSLATE_TIN_ORE.get());

        tag(Tags.Blocks.ORES_IN_GROUND_STONE)
                .add(MWBlocks.SILVER_ORE.get(), MWBlocks.TIN_ORE.get());

        tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE)
                .add(MWBlocks.DEEPSLATE_SILVER_ORE.get(), MWBlocks.DEEPSLATE_TIN_ORE.get());

        tag(Tags.Blocks.ORES)
                .addTags(MWTags.Blocks.ORES_SILVER, MWTags.Blocks.ORES_SILVER);

        tag(MWTags.Blocks.STORAGE_BLOCKS_STEEL)
                .add(MWBlocks.STEEL_BLOCK.get());

        tag(MWTags.Blocks.STORAGE_BLOCKS_SILVER)
                .add(MWBlocks.SILVER_BLOCK.get());

        tag(MWTags.Blocks.STORAGE_BLOCKS_BRONZE)
                .add(MWBlocks.BRONZE_BLOCK.get());

        tag(MWTags.Blocks.STORAGE_BLOCKS_TIN)
                .add(MWBlocks.TIN_BLOCK.get());

        tag(MWTags.Blocks.STORAGE_BLOCKS_RAW_SILVER)
                .add(MWBlocks.RAW_SILVER_BLOCK.get());

        tag(MWTags.Blocks.STORAGE_BLOCKS_RAW_TIN)
                .add(MWBlocks.RAW_TIN_BLOCK.get());

        tag(Tags.Blocks.STORAGE_BLOCKS)
                .addTags(
                        MWTags.Blocks.STORAGE_BLOCKS_STEEL,
                        MWTags.Blocks.STORAGE_BLOCKS_SILVER,
                        MWTags.Blocks.STORAGE_BLOCKS_BRONZE,
                        MWTags.Blocks.STORAGE_BLOCKS_TIN,
                        MWTags.Blocks.STORAGE_BLOCKS_RAW_SILVER,
                        MWTags.Blocks.STORAGE_BLOCKS_RAW_TIN
                );

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(
                        MWBlocks.STEEL_ANVIL.get(),
                        MWBlocks.SILVER_BLOCK.get(),
                        MWBlocks.SILVER_ORE.get(),
                        MWBlocks.RAW_SILVER_BLOCK.get(),
                        MWBlocks.STEEL_BLOCK.get(),
                        MWBlocks.DEEPSLATE_SILVER_ORE.get()
                );

        tag(Tags.Blocks.ORE_RATES_SINGULAR)
                .add(
                        MWBlocks.SILVER_ORE.get(),
                        MWBlocks.TIN_ORE.get(),
                        MWBlocks.DEEPSLATE_SILVER_ORE.get(),
                        MWBlocks.DEEPSLATE_TIN_ORE.get()
                );

    }
}
