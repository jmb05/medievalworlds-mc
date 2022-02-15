package net.jmb19905.medievalworlds;

import net.jmb19905.medievalworlds.client.ClientSetup;
import net.jmb19905.medievalworlds.client.networking.NetworkStartupClientOnly;
import net.jmb19905.medievalworlds.common.block.CustomFireBlock;
import net.jmb19905.medievalworlds.common.capability.MWCapabilityManager;
import net.jmb19905.medievalworlds.common.networking.NetworkStartupCommon;
import net.jmb19905.medievalworlds.common.world.gen.OreGenerator;
import net.jmb19905.medievalworlds.core.compatability.ExternalMods;
import net.jmb19905.medievalworlds.core.registries.*;
import net.jmb19905.medievalworlds.core.tabs.MWBlocksTab;
import net.jmb19905.medievalworlds.core.tabs.MWCombatTab;
import net.jmb19905.medievalworlds.core.tabs.MWMaterialsTab;
import net.jmb19905.medievalworlds.core.tabs.MWToolsTab;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import static net.jmb19905.medievalworlds.MedievalWorlds.MOD_ID;

@Mod(MOD_ID)
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MedievalWorlds {

    public static final String MOD_ID = "medievalworlds";

    public static MWBlocksTab blocksTab = new MWBlocksTab();
    public static MWMaterialsTab materialsTab = new MWMaterialsTab();
    public static MWToolsTab toolsTab = new MWToolsTab();
    public static MWCombatTab combatTab = new MWCombatTab();

    @SuppressWarnings("deprecation")
    public MedievalWorlds() {
        CustomRegistries.init();

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MWBlocks.BLOCKS.register(modEventBus);
        VanillaReplacements.BLOCKS.register(modEventBus);
        MWBlocks.BLOCK_ITEMS.register(modEventBus);
        MWItems.registerAllToolParts();
        MWItems.ITEMS.register(modEventBus);
        VanillaReplacements.ITEMS.register(modEventBus);
        MWEnchantments.ENCHANTMENTS.register(modEventBus);
        MWBlockEntityTypes.BLOCK_ENTITIES.register(modEventBus);
        MWMenuTypes.MENU_TYPES.register(modEventBus);
        MWRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        MWMetalMaterials.METAL_MATERIALS.register(modEventBus);

        registerCommonEvents();
        modEventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGenerator::registerBiomeModification);
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
        CustomFireBlock.bootstrap();
        OreGenerator.registerOres();

        MinecraftForge.EVENT_BUS.register(MWCapabilityManager.class);
    }

    @SubscribeEvent
    public static void enqueue(InterModEnqueueEvent event) {
        if(ExternalMods.CURIOS.isLoaded()) {
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());
        }
    }
}