package net.jmb19905.medievalworlds.util;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.registries.ContainerTypeRegistryHandler;
import net.jmb19905.medievalworlds.screen.AlloyFurnaceScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent evt){
        ScreenManager.registerFactory(ContainerTypeRegistryHandler.ALLOY_FURNACE.get(), AlloyFurnaceScreen::new);
    }

}
