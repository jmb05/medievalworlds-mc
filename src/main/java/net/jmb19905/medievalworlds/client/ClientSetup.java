package net.jmb19905.medievalworlds.client;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.ber.CustomAnvilRenderer;
import net.jmb19905.medievalworlds.client.screen.CustomAnvilScreen;
import net.jmb19905.medievalworlds.core.registries.MWBlockEntityTypes;
import net.jmb19905.medievalworlds.core.registries.MWMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static final ModelLayerLocation CUSTOM_ANVIL_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "anvil"), "main");

    private static MWBEWLR bewlr;

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent evt) {
        MenuScreens.register(MWMenuTypes.ANVIL_MENU.get(), CustomAnvilScreen::new);

        bewlr = new MWBEWLR();
    }

    public static MWBEWLR getBEWLR() {
        return bewlr;
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CUSTOM_ANVIL_LAYER, CustomAnvilRenderer::createBodyLayer);
    }

    @Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModEvents{

        @SubscribeEvent
        public static void registerERs(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(MWBlockEntityTypes.CUSTOM_ANVIL.get(), CustomAnvilRenderer::new);
        }

    }

}
