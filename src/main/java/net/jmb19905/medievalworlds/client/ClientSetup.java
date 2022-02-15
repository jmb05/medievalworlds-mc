package net.jmb19905.medievalworlds.client;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.ber.BellowsRenderer;
import net.jmb19905.medievalworlds.client.ber.CustomAnvilRenderer;
import net.jmb19905.medievalworlds.client.er.BannerLayer;
import net.jmb19905.medievalworlds.client.model.RoundShieldModel;
import net.jmb19905.medievalworlds.client.model.armor.CrownModel;
import net.jmb19905.medievalworlds.client.model.armor.GambesonModel;
import net.jmb19905.medievalworlds.client.model.armor.KnightArmorHelmetModel;
import net.jmb19905.medievalworlds.client.screen.AlloyFurnaceScreen;
import net.jmb19905.medievalworlds.client.screen.AnvilScreen;
import net.jmb19905.medievalworlds.client.screen.CustomSmithingScreen;
import net.jmb19905.medievalworlds.core.registries.MWMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class ClientSetup {

    public static final ModelLayerLocation KNIGHT_HELMET_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "knight_armor_helmet"), "main");
    public static final ModelLayerLocation GAMBESON_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "gambeson"), "main");
    public static final ModelLayerLocation CROWN_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "crown"), "main");
    public static final ModelLayerLocation CUSTOM_ANVIL_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "anvil"), "main");
    public static final ModelLayerLocation BELLOWS_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "bellows"), "main");
    public static final ModelLayerLocation ROUND_SHIELD_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "round_shield"), "main");
    public static final ModelLayerLocation BANNER_LOCATION = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "banner"), "main");

    private static MWBEWLR bewlr;

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent evt) {
        MenuScreens.register(MWMenuTypes.ALLOY_FURNACE.get(), AlloyFurnaceScreen::new);
        MenuScreens.register(MWMenuTypes.ANVIL_MENU.get(), AnvilScreen::new);
        MenuScreens.register(MWMenuTypes.SMITHING_MENU.get(), CustomSmithingScreen::new);

        CuriosRendererRegistry.register(Items.BLACK_BANNER, BannerLayer::new);
        CuriosRendererRegistry.register(Items.BLUE_BANNER, BannerLayer::new);
        CuriosRendererRegistry.register(Items.BROWN_BANNER, BannerLayer::new);
        CuriosRendererRegistry.register(Items.CYAN_BANNER, BannerLayer::new);
        CuriosRendererRegistry.register(Items.GRAY_BANNER, BannerLayer::new);
        CuriosRendererRegistry.register(Items.GREEN_BANNER, BannerLayer::new);
        CuriosRendererRegistry.register(Items.LIGHT_BLUE_BANNER, BannerLayer::new);
        CuriosRendererRegistry.register(Items.LIGHT_GRAY_BANNER, BannerLayer::new);
        CuriosRendererRegistry.register(Items.LIME_BANNER, BannerLayer::new);
        CuriosRendererRegistry.register(Items.MAGENTA_BANNER, BannerLayer::new);
        CuriosRendererRegistry.register(Items.ORANGE_BANNER, BannerLayer::new);
        CuriosRendererRegistry.register(Items.PINK_BANNER, BannerLayer::new);
        CuriosRendererRegistry.register(Items.PURPLE_BANNER, BannerLayer::new);
        CuriosRendererRegistry.register(Items.RED_BANNER, BannerLayer::new);
        CuriosRendererRegistry.register(Items.WHITE_BANNER, BannerLayer::new);
        CuriosRendererRegistry.register(Items.YELLOW_BANNER, BannerLayer::new);

        bewlr = new MWBEWLR();
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(KNIGHT_HELMET_LAYER, KnightArmorHelmetModel::createBodyLayer);
        event.registerLayerDefinition(GAMBESON_LAYER, GambesonModel::createBodyLayer);
        event.registerLayerDefinition(CROWN_LAYER, CrownModel::createBodyLayer);
        event.registerLayerDefinition(CUSTOM_ANVIL_LAYER, CustomAnvilRenderer::createBodyLayer);
        event.registerLayerDefinition(BELLOWS_LAYER, BellowsRenderer::createBodyLayer);
        event.registerLayerDefinition(ROUND_SHIELD_LAYER, RoundShieldModel::createBodyLayer);
        event.registerLayerDefinition(BANNER_LOCATION, BannerLayer::createBannerLayer);
    }

    public static MWBEWLR getBEWLR() {
        return bewlr;
    }
}