package net.jmb19905.medievalworlds.client;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.ber.BellowsRenderer;
import net.jmb19905.medievalworlds.client.ber.BloomeryRenderer;
import net.jmb19905.medievalworlds.client.ber.CustomAnvilRenderer;
import net.jmb19905.medievalworlds.client.ber.QuernRenderer;
import net.jmb19905.medievalworlds.client.er.CloakRenderer;
import net.jmb19905.medievalworlds.client.er.CustomBannerRenderer;
import net.jmb19905.medievalworlds.client.key.MWKeyHandler;
import net.jmb19905.medievalworlds.client.model.RoundShieldModel;
import net.jmb19905.medievalworlds.client.model.armor.CrownModel;
import net.jmb19905.medievalworlds.client.model.armor.GambesonModel;
import net.jmb19905.medievalworlds.client.model.armor.KettleHelmetModel;
import net.jmb19905.medievalworlds.client.model.armor.KnightArmorHelmetModel;
import net.jmb19905.medievalworlds.client.screen.*;
import net.jmb19905.medievalworlds.core.registries.MWBlockEntityTypes;
import net.jmb19905.medievalworlds.core.registries.MWMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class ClientSetup {

    public static final ModelLayerLocation KETTLE_HELMET_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "kettle_helmet"), "main");
    public static final ModelLayerLocation KNIGHT_HELMET_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "knight_armor_helmet"), "main");
    public static final ModelLayerLocation GAMBESON_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "gambeson"), "main");
    public static final ModelLayerLocation COIF_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "coif"), "main");
    public static final ModelLayerLocation CROWN_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "crown"), "main");
    public static final ModelLayerLocation CUSTOM_ANVIL_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "anvil"), "main");
    public static final ModelLayerLocation BELLOWS_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "bellows"), "main");
    public static final ModelLayerLocation ROUND_SHIELD_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "round_shield"), "main");
    public static final ModelLayerLocation QUERN_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "quern"), "main");
    public static final ModelLayerLocation CLOAK_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "cloak"), "main");
    public static final ModelLayerLocation GAMBESON_CURIO_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "gambeson_curio"), "main");
    public static final ModelLayerLocation COIF_CURIO_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "coif_curio"), "main");

    private static MWBEWLR bewlr;

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent evt) {
        MenuScreens.register(MWMenuTypes.ALLOY_FURNACE.get(), AlloyFurnaceScreen::new);
        MenuScreens.register(MWMenuTypes.ANVIL_MENU.get(), AnvilScreen::new);
        MenuScreens.register(MWMenuTypes.SMITHING_MENU.get(), CustomSmithingScreen::new);
        MenuScreens.register(MWMenuTypes.FLETCHING_MENU.get(), CustomFletchingScreen::new);
        MenuScreens.register(MWMenuTypes.ADVANCED_BLOOMERY_MENU.get(), AdvancedBloomeryScreen::new);

        bewlr = new MWBEWLR();

        MWKeyHandler.registerKeybindings();
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(KETTLE_HELMET_LAYER, KettleHelmetModel::createLayer);
        event.registerLayerDefinition(KNIGHT_HELMET_LAYER, KnightArmorHelmetModel::createBodyLayer);
        event.registerLayerDefinition(GAMBESON_LAYER, GambesonModel::createFullGambesonLayer);
        event.registerLayerDefinition(COIF_LAYER, GambesonModel::createFullCoifLayer);
        event.registerLayerDefinition(CROWN_LAYER, CrownModel::createBodyLayer);
        event.registerLayerDefinition(CUSTOM_ANVIL_LAYER, CustomAnvilRenderer::createBodyLayer);
        event.registerLayerDefinition(BELLOWS_LAYER, BellowsRenderer::createBodyLayer);
        event.registerLayerDefinition(ROUND_SHIELD_LAYER, RoundShieldModel::createBodyLayer);
        event.registerLayerDefinition(QUERN_LAYER, QuernRenderer::createBodyLayer);
        event.registerLayerDefinition(CLOAK_LAYER, CloakRenderer::createCloakLayer);
        event.registerLayerDefinition(GAMBESON_CURIO_LAYER, GambesonModel::createSmallGambesonLayer);
        event.registerLayerDefinition(COIF_CURIO_LAYER, GambesonModel::createSmallCoifLayer);

    }

    public static MWBEWLR getBEWLR() {
        return bewlr;
    }

    @Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModEvents{

        @SubscribeEvent
        public static void registerERs(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(MWBlockEntityTypes.CUSTOM_ANVIL.get(), CustomAnvilRenderer::new);
            event.registerBlockEntityRenderer(MWBlockEntityTypes.SIMPLE_BLOOMERY.get(), BloomeryRenderer::new);
            event.registerBlockEntityRenderer(MWBlockEntityTypes.BELLOWS.get(), BellowsRenderer::new);
            event.registerBlockEntityRenderer(MWBlockEntityTypes.QUERN.get(), QuernRenderer::new);

            CuriosRendererRegistry.register(Items.BLACK_BANNER, CustomBannerRenderer::new);
            CuriosRendererRegistry.register(Items.WHITE_BANNER, CustomBannerRenderer::new);
            CuriosRendererRegistry.register(Items.RED_BANNER, CustomBannerRenderer::new);
            CuriosRendererRegistry.register(Items.PINK_BANNER, CustomBannerRenderer::new);
            CuriosRendererRegistry.register(Items.MAGENTA_BANNER, CustomBannerRenderer::new);
            CuriosRendererRegistry.register(Items.PURPLE_BANNER, CustomBannerRenderer::new);
            CuriosRendererRegistry.register(Items.BLUE_BANNER, CustomBannerRenderer::new);
            CuriosRendererRegistry.register(Items.LIGHT_BLUE_BANNER, CustomBannerRenderer::new);
            CuriosRendererRegistry.register(Items.CYAN_BANNER, CustomBannerRenderer::new);
            CuriosRendererRegistry.register(Items.LIME_BANNER, CustomBannerRenderer::new);
            CuriosRendererRegistry.register(Items.GREEN_BANNER, CustomBannerRenderer::new);
            CuriosRendererRegistry.register(Items.YELLOW_BANNER, CustomBannerRenderer::new);
            CuriosRendererRegistry.register(Items.ORANGE_BANNER, CustomBannerRenderer::new);
            CuriosRendererRegistry.register(Items.BROWN_BANNER, CustomBannerRenderer::new);
            CuriosRendererRegistry.register(Items.GRAY_BANNER, CustomBannerRenderer::new);
            CuriosRendererRegistry.register(Items.LIGHT_GRAY_BANNER, CustomBannerRenderer::new);
        }
    }

    @Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ForgeEvents {
    }
}