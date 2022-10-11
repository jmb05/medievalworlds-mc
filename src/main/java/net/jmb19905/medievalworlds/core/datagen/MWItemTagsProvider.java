package net.jmb19905.medievalworlds.core.datagen;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.core.registries.MWItemTags;
import net.jmb19905.medievalworlds.core.registries.MWItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MWItemTagsProvider extends ItemTagsProvider {

    protected MWItemTagsProvider(DataGenerator generator, MWBlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(generator, blockTagsProvider, MedievalWorlds.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(MWItemTags.STAFFS)
                .add(MWItems.ACACIA_STAFF.get())
                .add(MWItems.BIRCH_STAFF.get())
                .add(MWItems.DARK_OAK_STAFF.get())
                .add(MWItems.JUNGLE_STAFF.get())
                .add(MWItems.MANGROVE_STAFF.get())
                .add(MWItems.OAK_STAFF.get())
                .add(MWItems.SPRUCE_STAFF.get());
    }
}
