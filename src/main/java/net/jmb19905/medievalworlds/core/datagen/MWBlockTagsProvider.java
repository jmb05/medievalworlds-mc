package net.jmb19905.medievalworlds.core.datagen;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MWBlockTagsProvider extends BlockTagsProvider {
    public MWBlockTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MedievalWorlds.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {

    }
}
