package net.jmb19905.medievalworlds.client;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.ber.BellowsRenderer;
import net.jmb19905.medievalworlds.client.ber.BloomeryRenderer;
import net.jmb19905.medievalworlds.client.ber.CustomAnvilRenderer;
import net.jmb19905.medievalworlds.client.ber.QuernRenderer;
import net.jmb19905.medievalworlds.client.er.BannerLayer;
import net.jmb19905.medievalworlds.client.model.RoundShieldModel;
import net.jmb19905.medievalworlds.client.model.armor.CrownModel;
import net.jmb19905.medievalworlds.client.model.armor.GambesonModel;
import net.jmb19905.medievalworlds.client.model.armor.KnightArmorHelmetModel;
import net.jmb19905.medievalworlds.client.screen.AlloyFurnaceScreen;
import net.jmb19905.medievalworlds.client.screen.AnvilScreen;
import net.jmb19905.medievalworlds.client.screen.CustomFletchingScreen;
import net.jmb19905.medievalworlds.client.screen.CustomSmithingScreen;
import net.jmb19905.medievalworlds.common.item.lance.LanceItem;
import net.jmb19905.medievalworlds.core.registries.MWBlockEntityTypes;
import net.jmb19905.medievalworlds.core.registries.MWMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
    public static final ModelLayerLocation QUERN_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "quern"), "main");

    private static MWBEWLR bewlr;

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent evt) {
        MenuScreens.register(MWMenuTypes.ALLOY_FURNACE.get(), AlloyFurnaceScreen::new);
        MenuScreens.register(MWMenuTypes.ANVIL_MENU.get(), AnvilScreen::new);
        MenuScreens.register(MWMenuTypes.SMITHING_MENU.get(), CustomSmithingScreen::new);
        MenuScreens.register(MWMenuTypes.FLETCHING_MENU.get(), CustomFletchingScreen::new);

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
        event.registerLayerDefinition(QUERN_LAYER, QuernRenderer::createBodyLayer);
    }

    public static MWBEWLR getBEWLR() {
        return bewlr;
    }

    @Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModEvents{

        @SubscribeEvent
        public static void registerERs(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(MWBlockEntityTypes.CUSTOM_ANVIL.get(), CustomAnvilRenderer::new);
            event.registerBlockEntityRenderer(MWBlockEntityTypes.BLOOMERY.get(), BloomeryRenderer::new);
            event.registerBlockEntityRenderer(MWBlockEntityTypes.BELLOWS.get(), BellowsRenderer::new);
            event.registerBlockEntityRenderer(MWBlockEntityTypes.QUERN.get(), QuernRenderer::new);
        }

    }

    @Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void renderPlayerPre(RenderPlayerEvent.Pre event) {
            PlayerRenderer renderer = event.getRenderer();
            AbstractClientPlayer player = (AbstractClientPlayer) event.getPlayer();
            if (player.getUsedItemHand() == InteractionHand.MAIN_HAND) {
                if (player.getUseItem().getItem() instanceof LanceItem) {
                    renderer.getModel().rightArm.visible = false;
                    renderer.getModel().rightSleeve.visible = false;
                }
            }
        }

        @SubscribeEvent
        public static void renderPlayerPost(RenderPlayerEvent.Post event) {
            PlayerRenderer renderer = event.getRenderer();
            AbstractClientPlayer player = (AbstractClientPlayer) event.getPlayer();
            if (player.getUsedItemHand() == InteractionHand.MAIN_HAND) {
                if (player.getUseItem().getItem() instanceof LanceItem) {
                    renderer.getModel().rightArm.visible = true;
                    //renderer.getModel().rightSleeve.visible = true;

                    renderer.getModel().rightArm.x = -Mth.cos((float) Math.toRadians(player.yBodyRot)) * 4.2f;
                    renderer.getModel().rightArm.y = player.isCrouching() ? 16.5f : 20.5f;
                    renderer.getModel().rightArm.z = -Mth.sin((float) Math.toRadians(player.yBodyRot)) * 5.0f;
                    renderer.getModel().rightArm.xRot = (float) Math.toRadians(180.0f);
                    renderer.getModel().rightArm.yRot = (float) -Math.toRadians(player.yBodyRot);
                    renderer.getModel().rightArm.zRot = 0.0f;
                    renderer.getModel().rightArm.render(event.getPoseStack(), event.getMultiBufferSource().getBuffer(RenderType.entitySolid(player.getSkinTextureLocation())), event.getPackedLight(), OverlayTexture.NO_OVERLAY);
                    renderer.getModel().rightArm.y = 2.0f;

                    renderer.getModel().rightSleeve.x = -Mth.cos((float) Math.toRadians(player.yBodyRot)) * 4.2f;
                    renderer.getModel().rightSleeve.y = player.isCrouching() ? 16.5f : 20.5f;
                    renderer.getModel().rightSleeve.z = -Mth.sin((float) Math.toRadians(player.yBodyRot)) * 5.0f;
                    renderer.getModel().rightSleeve.xRot = (float) Math.toRadians(180.0f);
                    renderer.getModel().rightSleeve.yRot = (float) -Math.toRadians(player.yBodyRot);
                    renderer.getModel().rightSleeve.zRot = 0.0f;
                    renderer.getModel().rightSleeve.render(event.getPoseStack(), event.getMultiBufferSource().getBuffer(RenderType.entitySolid(player.getSkinTextureLocation())), event.getPackedLight(), OverlayTexture.NO_OVERLAY);
                    renderer.getModel().rightSleeve.y = 2.0f;
                }
            }
        }

        @SubscribeEvent
        public static void renderPlayerHand(RenderHandEvent event) {

        }
    }
}