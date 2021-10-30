package net.jmb19905.medievalworlds.client;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.model.armor.KnightArmorHelmetModel;
import net.jmb19905.medievalworlds.client.screen.AlloyFurnaceScreen;
import net.jmb19905.medievalworlds.client.screen.BloomeryScreen;
import net.jmb19905.medievalworlds.client.screen.ForgeScreen;
import net.jmb19905.medievalworlds.client.ber.CustomAnvilRenderer;
import net.jmb19905.medievalworlds.core.registries.MWMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static final ModelLayerLocation KNIGHT_HELMET_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "knight_armor_helmet"), "main");
    public static final ModelLayerLocation CUSTOM_ANVIL_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "anvil"), "main");

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent evt) {
        MenuScreens.register(MWMenuTypes.ALLOY_FURNACE.get(), AlloyFurnaceScreen::new);
        MenuScreens.register(MWMenuTypes.BLOOMERY.get(), BloomeryScreen::new);
        MenuScreens.register(MWMenuTypes.FORGE.get(), ForgeScreen::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(KNIGHT_HELMET_LAYER, KnightArmorHelmetModel::createBodyLayer);
        event.registerLayerDefinition(CUSTOM_ANVIL_LAYER, CustomAnvilRenderer::createBodyLayer);
    }

}
