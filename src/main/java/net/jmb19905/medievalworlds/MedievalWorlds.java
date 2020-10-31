package net.jmb19905.medievalworlds;

import net.jmb19905.medievalworlds.networking.NetworkStartupClientOnly;
import net.jmb19905.medievalworlds.networking.NetworkStartupCommon;
import net.jmb19905.medievalworlds.registries.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static net.jmb19905.medievalworlds.MedievalWorlds.MOD_ID;

@Mod(MOD_ID)
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MedievalWorlds {

    public static final String MOD_ID = "medievalworlds";

    public MedievalWorlds() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockRegistryHandler.BLOCKS.register(modEventBus);
        BlockRegistryHandler.BLOCK_ITEMS.register(modEventBus);
        ItemRegistryHandler.ITEMS.register(modEventBus);
        EnchantmentRegistryHandler.ENCHANTMENTS.register(modEventBus);
        TileEntityTypeRegistryHandler.TILE_ENTITY_TYPES.register(modEventBus);
        ContainerTypeRegistryHandler.CONTAINER_TYPES.register(modEventBus);
        RecipeSerializerRegistryHandler.RECIPE_SERIALIZERS.register(modEventBus);

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

}
