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
        simpleBlock(MWBlocks.CHARCOAL_PLANKS.get());
        axisBlock(MWBlocks.CHARCOAL_LOG.get());
    }
}
