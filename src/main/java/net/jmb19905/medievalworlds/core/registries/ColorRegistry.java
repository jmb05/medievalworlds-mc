package net.jmb19905.medievalworlds.core.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorRegistry {

    @SubscribeEvent
    public static void registerBlockColors(ColorHandlerEvent.Block event) {
        event.getBlockColors().register((state, tintGetter, pos, tint)
                -> tintGetter != null && pos != null ? BiomeColors.getAverageWaterColor(tintGetter, pos) : -1,
                MWBlocks.SLACK_TUB_BLOCK.get());
        System.out.println("Registered BlockColors");
    }

    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event) {
        System.out.println("Registered ItemColors");
    }

}
