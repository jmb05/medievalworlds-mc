package net.jmb19905.medievalworlds.core.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.item.CloakItem;
import net.jmb19905.medievalworlds.common.item.MWSwordWeapon;
import net.jmb19905.medievalworlds.common.item.MWTiers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class MWItems {

    private static List<? extends Item> toolItemOrder;
    private static List<? extends Item> materialItemOrder;
    private static List<? extends Item> combatItemOrder;

    private static final List<RegistryObject<Item>> toolItemRegistryOrder = new ArrayList<>();
    private static final List<RegistryObject<Item>> materialItemRegistryOrder = new ArrayList<>();
    private static final List<RegistryObject<Item>> combatItemRegistryOrder = new ArrayList<>();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MedievalWorlds.MOD_ID);

    public static final RegistryObject<Item> LONGSWORD = registerCombatItem("longsword", () -> new MWSwordWeapon(Tiers.IRON, 10, -2.4f, 1.0f, new Item.Properties().tab(MedievalWorlds.combatTab).stacksTo(1)));
    public static final RegistryObject<CloakItem> CLOAK = registerTool("cloak", () -> new CloakItem(new Item.Properties().tab(MedievalWorlds.toolsTab).stacksTo(1)));
    public static final RegistryObject<CloakItem> DARK_CLOAK = registerTool("dark_cloak", () -> new CloakItem(new Item.Properties().tab(MedievalWorlds.toolsTab).stacksTo(1)));
    public static final RegistryObject<CloakItem> LIGHT_CLOAK = registerTool("light_cloak", () -> new CloakItem(new Item.Properties().tab(MedievalWorlds.toolsTab).stacksTo(1)));
    public static final RegistryObject<Item> OAK_STAFF = registerCombatItem("oak_staff", () -> new MWSwordWeapon(MWTiers.STURDY_WOOD, 3, -2.4f, 3.0f, new Item.Properties().tab(MedievalWorlds.combatTab).stacksTo(1)));
    public static final RegistryObject<Item> SPRUCE_STAFF = registerCombatItem("spruce_staff", () -> new MWSwordWeapon(MWTiers.STURDY_WOOD, 3, -2.4f, 3.0f, new Item.Properties().tab(MedievalWorlds.combatTab).stacksTo(1)));
    public static final RegistryObject<Item> DARK_OAK_STAFF = registerCombatItem("dark_oak_staff", () -> new MWSwordWeapon(MWTiers.STURDY_WOOD, 3, -2.4f, 3.0f, new Item.Properties().tab(MedievalWorlds.combatTab).stacksTo(1)));
    public static final RegistryObject<Item> ACACIA_STAFF = registerCombatItem("acacia_staff", () -> new MWSwordWeapon(MWTiers.STURDY_WOOD, 3, -2.4f, 3.0f, new Item.Properties().tab(MedievalWorlds.combatTab).stacksTo(1)));
    public static final RegistryObject<Item> BIRCH_STAFF = registerCombatItem("birch_staff", () -> new MWSwordWeapon(MWTiers.STURDY_WOOD, 3, -2.4f, 3.0f, new Item.Properties().tab(MedievalWorlds.combatTab).stacksTo(1)));
    public static final RegistryObject<Item> JUNGLE_STAFF = registerCombatItem("jungle_staff", () -> new MWSwordWeapon(MWTiers.STURDY_WOOD, 3, -2.4f, 3.0f, new Item.Properties().tab(MedievalWorlds.combatTab).stacksTo(1)));
    public static final RegistryObject<Item> MANGROVE_STAFF = registerCombatItem("mangrove_staff", () -> new MWSwordWeapon(MWTiers.STURDY_WOOD, 3, -2.4f, 3.0f, new Item.Properties().tab(MedievalWorlds.combatTab).stacksTo(1)));

    private static <T extends Item> RegistryObject<T> registerMaterial(String id, Supplier<T> itemSupplier) {
        RegistryObject<T> regObj = ITEMS.register(id, itemSupplier);
        materialItemRegistryOrder.add((RegistryObject<Item>) regObj);
        return regObj;
    }

    private static <T extends Item> RegistryObject<T> registerTool(String id, Supplier<T> itemSupplier) {
        RegistryObject<T> regObj = ITEMS.register(id, itemSupplier);
        toolItemRegistryOrder.add((RegistryObject<Item>) regObj);
        return regObj;
    }

    private static <T extends Item> RegistryObject<T> registerCombatItem(String id, Supplier<T> itemSupplier) {
        RegistryObject<T> regObj = ITEMS.register(id, itemSupplier);
        combatItemRegistryOrder.add((RegistryObject<Item>) regObj);
        return regObj;
    }

    public static List<? extends Item> getToolItemOrder() {
        if(toolItemOrder == null) {
            toolItemOrder = createOrderList(toolItemRegistryOrder);
        }
        return toolItemOrder;
    }

    public static List<? extends Item> getMaterialsItemOrder() {
        if(materialItemOrder == null) {
            materialItemOrder = createOrderList(materialItemRegistryOrder);
        }
        return materialItemOrder;
    }

    public static List<? extends Item> getCombatItemOrder() {
        if(combatItemOrder == null) {
            combatItemOrder = createOrderList(combatItemRegistryOrder);
        }
        return combatItemOrder;
    }

    public static RegistryObject<Item> getEntry(ResourceLocation id) {
        return ITEMS.getEntries().stream().filter(ro -> ro.getId().equals(id)).findFirst().orElse(null);
    }

    private static <T extends Item> List<T> createOrderList(List<RegistryObject<T>> registries){
        return registries.stream().collect(ArrayList::new, (list, regObj) -> list.add(regObj.get()), ArrayList::addAll);
    }

}
