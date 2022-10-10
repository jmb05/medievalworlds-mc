package net.jmb19905.medievalworlds.core.datagen;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MWItemModelProvider extends ItemModelProvider {

    public MWItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MedievalWorlds.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
