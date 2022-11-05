package net.jmb19905.medievalworlds.common.datagen;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.item.weapon.MWArrowItem;
import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.jmb19905.medievalworlds.common.registries.MWTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class MWItemTagsProvider extends ItemTagsProvider {

    protected MWItemTagsProvider(DataGenerator generator, MWBlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(generator, blockTagsProvider, MedievalWorlds.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(MWTags.Items.STAFFS)
                .add(
                        MWItems.ACACIA_STAFF.get(),
                        MWItems.BIRCH_STAFF.get(),
                        MWItems.DARK_OAK_STAFF.get(),
                        MWItems.JUNGLE_STAFF.get(),
                        MWItems.MANGROVE_STAFF.get(),
                        MWItems.OAK_STAFF.get(),
                        MWItems.SPRUCE_STAFF.get()
                );
        tag(Tags.Items.TOOLS_SHIELDS)
                .add(
                        MWItems.ACACIA_STAFF.get(),
                        MWItems.BIRCH_STAFF.get(),
                        MWItems.DARK_OAK_STAFF.get(),
                        MWItems.JUNGLE_STAFF.get(),
                        MWItems.MANGROVE_STAFF.get(),
                        MWItems.OAK_STAFF.get(),
                        MWItems.SPRUCE_STAFF.get()
                );
        tag(MWTags.Items.FLETCHING)
                .addTag(Tags.Items.FEATHERS);
        tag(MWTags.Items.INGOTS_STEEL)
                .add(MWItems.STEEL_INGOT.get());
        tag(MWTags.Items.INGOTS_SILVER)
                .add(MWItems.SILVER_INGOT.get());
        tag(MWTags.Items.INGOTS_BRONZE)
                .add(MWItems.BRONZE_INGOT.get());
        tag(MWTags.Items.INGOTS_TIN)
                .add(MWItems.TIN_NUGGET.get());
        tag(MWTags.Items.NUGGETS_STEEL)
                .add(MWItems.STEEL_NUGGET.get());
        tag(MWTags.Items.NUGGETS_SILVER)
                .add(MWItems.SILVER_NUGGET.get());
        tag(MWTags.Items.NUGGETS_BRONZE)
                .add(MWItems.BRONZE_NUGGET.get());
        tag(MWTags.Items.NUGGETS_TIN)
                .add(MWItems.TIN_NUGGET.get());

        tag(ItemTags.ARROWS)
                .add(MWItems.ARROWS.values().stream().map(RegistryObject::get).toArray(MWArrowItem[]::new));

        copy(MWTags.Blocks.ORES_SILVER, MWTags.Items.ORES_SILVER);
        copy(MWTags.Blocks.ORES_TIN, MWTags.Items.ORES_TIN);
        copy(Tags.Blocks.ORES, Tags.Items.ORES);
        copy(Tags.Blocks.ORES_IN_GROUND_STONE, Tags.Items.ORES_IN_GROUND_STONE);
        copy(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE, Tags.Items.ORES_IN_GROUND_DEEPSLATE);
        copy(MWTags.Blocks.STORAGE_BLOCKS_STEEL, MWTags.Items.STORAGE_BLOCKS_STEEL);
        copy(MWTags.Blocks.STORAGE_BLOCKS_SILVER, MWTags.Items.STORAGE_BLOCKS_SILVER);
        copy(MWTags.Blocks.STORAGE_BLOCKS_BRONZE, MWTags.Items.STORAGE_BLOCKS_BRONZE);
        copy(MWTags.Blocks.STORAGE_BLOCKS_TIN, MWTags.Items.STORAGE_BLOCKS_TIN);
        copy(MWTags.Blocks.STORAGE_BLOCKS_RAW_SILVER, MWTags.Items.STORAGE_BLOCKS_RAW_SILVER);
        copy(MWTags.Blocks.STORAGE_BLOCKS_RAW_TIN, MWTags.Items.STORAGE_BLOCKS_RAW_TIN);
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);
        copy(Tags.Blocks.ORE_RATES_SINGULAR, Tags.Items.ORE_RATES_SINGULAR);
    }
}
