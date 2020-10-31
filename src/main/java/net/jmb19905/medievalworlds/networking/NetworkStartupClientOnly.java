package net.jmb19905.medievalworlds.networking;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * User: The Grey Ghost
 * Date: 24/12/2014
 *
 *  See MinecraftByExample class for more information
 */
public class NetworkStartupClientOnly
{
    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        // not actually required for this example....
    }
}