package net.jmb19905.medievalworlds;

import net.jmb19905.medievalworlds.registries.*;
import net.jmb19905.medievalworlds.world.gen.OreGenerator;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.jmb19905.medievalworlds.MedievalWorlds.MOD_ID;

//Jared is cool
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
    }

}
