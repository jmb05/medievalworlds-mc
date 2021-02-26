package net.jmb19905.medievalworlds;

import net.jmb19905.medievalworlds.networking.NetworkStartupClientOnly;
import net.jmb19905.medievalworlds.networking.NetworkStartupCommon;
import net.jmb19905.medievalworlds.registries.*;
import net.jmb19905.medievalworlds.world.gen.OreGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.jmb19905.medievalworlds.MedievalWorlds.MOD_ID;

@Mod(MOD_ID)
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MedievalWorlds {

    public final static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "medievalworlds";

    public static MWItemGroup itemGroup = new MWItemGroup();

    public MedievalWorlds() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockRegistryHandler.BLOCKS.register(modEventBus);
        OreGenerator.ORE_FEATURES.register(modEventBus);
        BlockRegistryHandler.BLOCK_ITEMS.register(modEventBus);
        ItemRegistryHandler.ITEMS.register(modEventBus);
        EnchantmentRegistryHandler.ENCHANTMENTS.register(modEventBus);
        TileEntityTypeRegistryHandler.TILE_ENTITY_TYPES.register(modEventBus);
        ContainerTypeRegistryHandler.CONTAINER_TYPES.register(modEventBus);
        RecipeSerializerRegistryHandler.RECIPE_SERIALIZERS.register(modEventBus);

        modEventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGenerator::generateOres);
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
    }

    public void setup(final FMLCommonSetupEvent event) {

    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {

        /**
         * This method will be called by Forge when it is time for the mod to register features.
         */
        @SubscribeEvent
        public static void onRegisterFeatures(final RegistryEvent.Register<Feature<?>> event)
        {
            //FeatureInit.registerFeatures(event);
            //LOGGER.info("features/structures registered.");
        }
    }

    public static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T entry, String registryKey)
    {
        entry.setRegistryName(new ResourceLocation(MedievalWorlds.MOD_ID, registryKey));
        registry.register(entry);
        return entry;
    }

    public static class MWItemGroup extends ItemGroup{

        public MWItemGroup() {
            super("MedievalWorlds");
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemRegistryHandler.DIAMOND_KNIGHT_HELMET.get());
        }
    }

}
