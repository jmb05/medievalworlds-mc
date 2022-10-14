package net.jmb19905.medievalworlds;

import com.mojang.logging.LogUtils;
import net.jmb19905.medievalworlds.client.ClientSetup;
import net.jmb19905.medievalworlds.client.networking.NetworkStartupClientOnly;
import net.jmb19905.medievalworlds.common.capability.MWCapabilityManager;
import net.jmb19905.medievalworlds.common.compatability.CuriosCompat;
import net.jmb19905.medievalworlds.common.networking.NetworkStartupCommon;
import net.jmb19905.medievalworlds.common.registries.*;
import net.jmb19905.medievalworlds.common.tabs.MWBlocksTab;
import net.jmb19905.medievalworlds.common.tabs.MWCombatTab;
import net.jmb19905.medievalworlds.common.tabs.MWMaterialsTab;
import net.jmb19905.medievalworlds.common.tabs.MWToolsTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(MedievalWorlds.MOD_ID)
@Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MedievalWorlds {

    public static final String MOD_ID = "medievalworlds";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static MWBlocksTab blocksTab = new MWBlocksTab();
    public static MWCombatTab combatTab = new MWCombatTab();
    public static MWMaterialsTab materialsTab = new MWMaterialsTab();
    public static MWToolsTab toolsTab = new MWToolsTab();

    public MedievalWorlds() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        VanillaOverride.BLOCKS.register(modEventBus);
        VanillaOverride.ITEMS.register(modEventBus);
        MWItems.ITEMS.register(modEventBus);
        MWBlocks.BLOCKS.register(modEventBus);
        MWBlocks.BLOCK_ITEMS.register(modEventBus);
        MWBlockEntityTypes.BLOCK_ENTITIES.register(modEventBus);
        MWMenuTypes.MENU_TYPES.register(modEventBus);
        MWRecipeSerializers.RECIPE_SERIAlIZER.register(modEventBus);

        registerCommonEvents();
        modEventBus.addListener(this::commonSetup);
        //noinspection deprecation
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

    private void commonSetup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(MWCapabilityManager.class);
    }

    @SubscribeEvent
    public static void enqueue(InterModEnqueueEvent event) {
        CuriosCompat.createSlots();
    }
}
