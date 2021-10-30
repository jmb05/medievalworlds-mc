package net.jmb19905.medievalworlds;

import net.jmb19905.medievalworlds.client.ClientSetup;
import net.jmb19905.medievalworlds.client.networking.NetworkStartupClientOnly;
import net.jmb19905.medievalworlds.common.capability.CapabilityAttachEventHandler;
import net.jmb19905.medievalworlds.common.capability.motion.Motion;
import net.jmb19905.medievalworlds.common.networking.NetworkStartupCommon;
import net.jmb19905.medievalworlds.core.registries.*;
import net.jmb19905.medievalworlds.common.world.gen.OreGenerator;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;

import static net.jmb19905.medievalworlds.MedievalWorlds.MOD_ID;

@Mod(MOD_ID)
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MedievalWorlds {

    public static final String MOD_ID = "medievalworlds";

    public static MWCreativeTab tab = new MWCreativeTab();

    @SuppressWarnings("deprecation")
    public MedievalWorlds() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MWBlocks.BLOCKS.register(modEventBus);
        VanillaReplacements.BLOCKS.register(modEventBus);
        MWBlocks.BLOCK_ITEMS.register(modEventBus);
        VanillaReplacements.ITEMS.register(modEventBus);
        MWItems.ITEMS.register(modEventBus);
        MWEnchantments.ENCHANTMENTS.register(modEventBus);
        MWBlockEntityTypes.BLOCK_ENTITIES.register(modEventBus);
        MWMenuTypes.MENU_TYPES.register(modEventBus);
        MWRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);

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

        MinecraftForge.EVENT_BUS.register(CapabilityAttachEventHandler.class);
    }

    public static class MWCreativeTab extends CreativeModeTab {

        public MWCreativeTab() {
            super("MedievalWorlds");
        }

        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(MWItems.DIAMOND_KNIGHT_HELMET.get());
        }
    }

}
