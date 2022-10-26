package net.jmb19905.medievalworlds.client;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.armPoses.StaffArmPose;
import net.jmb19905.medievalworlds.client.key.MWKeyHandler;
import net.jmb19905.medievalworlds.client.renderers.armor.GambesonModel;
import net.jmb19905.medievalworlds.client.renderers.armor.KnightArmorHelmetModel;
import net.jmb19905.medievalworlds.client.renderers.blockentity.CustomAnvilRenderer;
import net.jmb19905.medievalworlds.client.renderers.curio.CloakRenderer;
import net.jmb19905.medievalworlds.client.renderers.curio.QuiverRenderer;
import net.jmb19905.medievalworlds.client.screen.AlloyFurnaceScreen;
import net.jmb19905.medievalworlds.client.screen.CustomAnvilScreen;
import net.jmb19905.medievalworlds.client.screen.CustomSmithingScreen;
import net.jmb19905.medievalworlds.client.tooltip.ClientQuiverTooltip;
import net.jmb19905.medievalworlds.common.capability.MWCapabilityManager;
import net.jmb19905.medievalworlds.common.capability.quiverInv.QuiverInv;
import net.jmb19905.medievalworlds.common.item.quiver.QuiverTooltip;
import net.jmb19905.medievalworlds.common.registries.MWBlockEntityTypes;
import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.jmb19905.medievalworlds.common.registries.MWMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static final ModelLayerLocation CUSTOM_ANVIL_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "anvil"), "main");
    public static final ModelLayerLocation CLOAK_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "cloak"), "main");
    public static final ModelLayerLocation QUIVER_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "quiver"),"main");
    public static final ModelLayerLocation KNIGHT_HELMET_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "knight_armor_helmet"), "main");
    public static final ModelLayerLocation GAMBESON_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "gambeson"), "main");

    public static HumanoidModel.ArmPose CUSTOM;

    private static MWBEWLR bewlr;

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent evt) {
        MenuScreens.register(MWMenuTypes.ANVIL.get(), CustomAnvilScreen::new);
        MenuScreens.register(MWMenuTypes.ALLOY.get(), AlloyFurnaceScreen::new);
        MenuScreens.register(MWMenuTypes.SMITHING.get(), CustomSmithingScreen::new);

        bewlr = new MWBEWLR();

        CUSTOM = HumanoidModel.ArmPose.create("CUSTOM", false, new StaffArmPose());

        evt.enqueueWork(
                () -> ItemProperties.register(MWItems.QUIVER.get(), new ResourceLocation(MedievalWorlds.MOD_ID, "filled"),
                (stack, level, living, id) -> {
                    QuiverInv inv = MWCapabilityManager.retrieveCapability(stack, QuiverInv.QUIVER_INV_CAPABILITY);
                    return inv == null ? 0 : inv.getFillLevel();
                }));
    }

    @SubscribeEvent
    public static void registerTooltipFactories(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(QuiverTooltip.class, ClientQuiverTooltip::new);
    }

    public static MWBEWLR getBEWLR() {
        return bewlr;
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CUSTOM_ANVIL_LAYER, CustomAnvilRenderer::createBodyLayer);
        event.registerLayerDefinition(CLOAK_LAYER, CloakRenderer::createCloakLayer);
        event.registerLayerDefinition(QUIVER_LAYER, QuiverRenderer::createQuiverLayer);
        event.registerLayerDefinition(KNIGHT_HELMET_LAYER, KnightArmorHelmetModel::createBodyLayer);
        event.registerLayerDefinition(GAMBESON_LAYER, GambesonModel::createGambesonLayer);
    }

    @Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModEvents{

        @SubscribeEvent
        public static void registerERs(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(MWBlockEntityTypes.CUSTOM_ANVIL.get(), CustomAnvilRenderer::new);
        }

        @SubscribeEvent
        public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
            event.register(MWKeyHandler.hoodKey);
        }

    }

}
