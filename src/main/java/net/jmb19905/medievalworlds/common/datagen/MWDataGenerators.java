package net.jmb19905.medievalworlds.common.datagen;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MWDataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var existingFileHelper = event.getExistingFileHelper();
        var blockTagsProv = new MWBlockTagsProvider(generator, existingFileHelper);

        generator.addProvider(true, new MWRecipeProvider(generator));
        generator.addProvider(true, new MWLootTableProvider(generator));
        generator.addProvider(true, new MWItemModelProvider(generator, existingFileHelper));
        generator.addProvider(true, new MWBlockStateProvider(generator, existingFileHelper));
        generator.addProvider(true, blockTagsProv);
        generator.addProvider(true, new MWItemTagsProvider(generator, blockTagsProv, existingFileHelper));
        generator.addProvider(true, new MWLangProvider(generator));
    }

}