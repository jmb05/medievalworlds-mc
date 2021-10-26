package net.jmb19905.medievalworlds;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.jmb19905.medievalworlds.client.model.armor.KnightArmorHelmetModel;
import net.jmb19905.medievalworlds.client.networking.NetworkStartupClientOnly;
import net.jmb19905.medievalworlds.client.screen.AlloyFurnaceScreen;
import net.jmb19905.medievalworlds.client.screen.BloomeryScreen;
import net.jmb19905.medievalworlds.common.capability.CapabilityAttachEventHandler;
import net.jmb19905.medievalworlds.common.capability.Motion;
import net.jmb19905.medievalworlds.common.networking.NetworkStartupCommon;
import net.jmb19905.medievalworlds.common.registries.*;
import net.jmb19905.medievalworlds.common.world.gen.OreGenerator;
import net.jmb19905.medievalworlds.core.MWEventBusSubscriber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;
import java.net.Proxy;

import static net.jmb19905.medievalworlds.MedievalWorlds.MOD_ID;

@Mod(MOD_ID)
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MedievalWorlds {

    public static final String MOD_ID = "medievalworlds";

    public static MWCreativeTab tab = new MWCreativeTab();

    @SuppressWarnings("deprecation")
    public MedievalWorlds() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockRegistryHandler.BLOCKS.register(modEventBus);
        BlockRegistryHandler.BLOCK_ITEMS.register(modEventBus);
        ItemRegistryHandler.ITEMS.register(modEventBus);
        EnchantmentRegistryHandler.ENCHANTMENTS.register(modEventBus);
        BlockEntityTypeRegistryHandler.BLOCK_ENTITIES.register(modEventBus);
        MenuTypeRegistryHandler.MENU_TYPES.register(modEventBus);
        RecipeSerializerRegistryHandler.RECIPE_SERIALIZERS.register(modEventBus);

        modEventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGenerator::registerBiomeModification);
        registerCommonEvents();
        DistExecutor.runWhenOn(Dist.CLIENT, () -> MedievalWorlds::registerClientOnlyEvents);
    }

    public static void registerCommonEvents(){
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(NetworkStartupCommon.class);
    }

    public static void registerClientOnlyEvents(){
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(NetworkStartupClientOnly.class);
        modEventBus.register(ClientSetup.class);
    }

    public void setup(final FMLCommonSetupEvent event) {
        Motion.register();
        registerCommonEvents();
        OreGenerator.registerOres();

        MinecraftForge.EVENT_BUS.register(MWEventBusSubscriber.class);
        MinecraftForge.EVENT_BUS.register(CapabilityAttachEventHandler.class);
    }

    public static class ClientSetup {

        public static final ModelLayerLocation KNIGHT_HELMET_LAYER = new ModelLayerLocation(new ResourceLocation(MedievalWorlds.MOD_ID, "knight_armor_helmet"), "main");

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent evt){
            MenuScreens.register(MenuTypeRegistryHandler.ALLOY_FURNACE.get(), AlloyFurnaceScreen::new);
            MenuScreens.register(MenuTypeRegistryHandler.BLOOMERY.get(), BloomeryScreen::new);
            System.out.println("Client setup");
        }

        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(KNIGHT_HELMET_LAYER, KnightArmorHelmetModel::createBodyLayer);
            System.out.println("Registered Layer: " + KNIGHT_HELMET_LAYER);
        }

    }

    public static class MWCreativeTab extends CreativeModeTab {

        public MWCreativeTab() {
            super("MedievalWorlds");
        }

        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemRegistryHandler.DIAMOND_KNIGHT_HELMET.get());
        }
    }

}
