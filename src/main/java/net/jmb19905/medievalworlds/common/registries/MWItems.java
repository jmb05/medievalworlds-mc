package net.jmb19905.medievalworlds.common.registries;

import com.google.common.collect.ImmutableMap;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.item.*;
import net.jmb19905.medievalworlds.common.item.armor.*;
import net.jmb19905.medievalworlds.common.item.heated.HeatedItem;
import net.jmb19905.medievalworlds.common.item.silver.*;
import net.jmb19905.medievalworlds.common.item.weapon.MWAxeWeapon;
import net.jmb19905.medievalworlds.common.item.weapon.MWSwordWeapon;
import net.jmb19905.medievalworlds.common.item.weapon.StaffItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings({"unchecked", "SameParameterValue"})
public class MWItems {

    private static List<? extends Item> toolItemOrder;
    private static List<? extends Item> materialItemOrder;
    private static List<? extends Item> combatItemOrder;
    public static List<BlockItem> blockItemOrder;

    private static final List<RegistryObject<BlockItem>> blockItemRegistryOrder = new ArrayList<>();
    private static final List<RegistryObject<Item>> toolItemRegistryOrder = new ArrayList<>();
    private static final List<RegistryObject<Item>> materialItemRegistryOrder = new ArrayList<>();
    private static final List<RegistryObject<Item>> combatItemRegistryOrder = new ArrayList<>();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MedievalWorlds.MOD_ID);

    public static final RegistryObject<CloakItem> CLOAK = registerTool("cloak", () -> new CloakItem(new Item.Properties().tab(MedievalWorlds.toolsTab).stacksTo(1)));
    public static final RegistryObject<CloakItem> DARK_CLOAK = registerTool("dark_cloak", () -> new CloakItem(new Item.Properties().tab(MedievalWorlds.toolsTab).stacksTo(1)));
    public static final RegistryObject<CloakItem> LIGHT_CLOAK = registerTool("light_cloak", () -> new CloakItem(new Item.Properties().tab(MedievalWorlds.toolsTab).stacksTo(1)));

    public static final RegistryObject<Item> QUIVER = registerTool("quiver", () -> new QuiverItem(3, new Item.Properties().stacksTo(1).tab(MedievalWorlds.toolsTab)));

    public static final RegistryObject<Item> OAK_STAFF = registerCombatItem("oak_staff", StaffItem::new);
    public static final RegistryObject<Item> SPRUCE_STAFF = registerCombatItem("spruce_staff", StaffItem::new);
    public static final RegistryObject<Item> DARK_OAK_STAFF = registerCombatItem("dark_oak_staff", StaffItem::new);
    public static final RegistryObject<Item> ACACIA_STAFF = registerCombatItem("acacia_staff", StaffItem::new);
    public static final RegistryObject<Item> BIRCH_STAFF = registerCombatItem("birch_staff", StaffItem::new);
    public static final RegistryObject<Item> JUNGLE_STAFF = registerCombatItem("jungle_staff", StaffItem::new);
    public static final RegistryObject<Item> MANGROVE_STAFF = registerCombatItem("mangrove_staff", StaffItem::new);

    public static final RegistryObject<Item> STEEL_INGOT = registerMaterial("steel_ingot", () -> new Item(new Item.Properties().stacksTo(64).tab(MedievalWorlds.materialsTab)));
    public static final RegistryObject<Item> SILVER_INGOT = registerMaterial("silver_ingot", () -> new Item(new Item.Properties().stacksTo(64).tab(MedievalWorlds.materialsTab)));
    public static final RegistryObject<Item> BRONZE_INGOT = registerMaterial("bronze_ingot", () -> new Item(new Item.Properties().stacksTo(64).tab(MedievalWorlds.materialsTab)));
    public static final RegistryObject<Item> TIN_INGOT = registerMaterial("tin_ingot", () -> new Item(new Item.Properties().stacksTo(64).tab(MedievalWorlds.materialsTab)));

    public static final RegistryObject<Item> STEEL_NUGGET = registerMaterial("steel_nugget", () -> new Item(new Item.Properties().stacksTo(64).tab(MedievalWorlds.materialsTab)));
    public static final RegistryObject<Item> SILVER_NUGGET = registerMaterial("silver_nugget", () -> new Item(new Item.Properties().stacksTo(64).tab(MedievalWorlds.materialsTab)));
    public static final RegistryObject<Item> BRONZE_NUGGET = registerMaterial("bronze_nugget", () -> new Item(new Item.Properties().stacksTo(64).tab(MedievalWorlds.materialsTab)));
    public static final RegistryObject<Item> TIN_NUGGET = registerMaterial("tin_nugget", () -> new Item(new Item.Properties().stacksTo(64).tab(MedievalWorlds.materialsTab)));


    public static final RegistryObject<Item> RAW_SILVER = registerMaterial("raw_silver", () -> new Item(new Item.Properties().stacksTo(64).tab(MedievalWorlds.materialsTab)));
    public static final RegistryObject<Item> RAW_TIN = registerMaterial("raw_tin", () -> new Item(new Item.Properties().stacksTo(64).tab(MedievalWorlds.materialsTab)));

    public static final RegistryObject<HeatedItem> HEATED_IRON_INGOT = registerMaterial("heated_iron_ingot", () -> new HeatedItem(Items.IRON_INGOT, new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));
    public static final RegistryObject<HeatedItem> HEATED_STEEL_INGOT = registerMaterial("heated_steel_ingot", () -> new HeatedItem(MWItems.STEEL_INGOT.get(), new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));
    public static final RegistryObject<HeatedItem> HEATED_COPPER_INGOT = registerMaterial("heated_copper_ingot", () -> new HeatedItem(Items.COPPER_INGOT, new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));
    public static final RegistryObject<HeatedItem> HEATED_BRONZE_INGOT = registerMaterial("heated_bronze_ingot", () -> new HeatedItem(MWItems.BRONZE_INGOT.get(), new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));
    public static final RegistryObject<HeatedItem> HEATED_SILVER_INGOT = registerMaterial("heated_silver_ingot", () -> new HeatedItem(MWItems.SILVER_INGOT.get(), new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));
    public static final RegistryObject<HeatedItem> HEATED_GOLD_INGOT = registerMaterial("heated_gold_ingot", () -> new HeatedItem(Items.GOLD_INGOT, new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));
    public static final RegistryObject<HeatedItem> HEATED_NETHERITE_INGOT = registerMaterial("heated_netherite_ingot", () -> new HeatedItem(Items.NETHERITE_INGOT, new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64).fireResistant()));

    public static final Map<String, RegistryObject<HeatedItem>> HEATED_INGOTS = ImmutableMap.of(
            "iron", HEATED_IRON_INGOT,
            "steel", HEATED_STEEL_INGOT,
            "copper", HEATED_COPPER_INGOT,
            "bronze", HEATED_BRONZE_INGOT,
            "silver", HEATED_SILVER_INGOT,
            "gold", HEATED_GOLD_INGOT,
            "netherite", HEATED_NETHERITE_INGOT
    );

    public static final Map<String, RegistryObject<Item>> TOOL_PARTS = addToolParts();

    public static Map<String, RegistryObject<Item>> addToolParts() {
        Map<String, RegistryObject<Item>> toolParts = new HashMap<>();
        String[] toolMaterials = {"iron", "steel", "bronze", "silver", "gold"};//excluding netherite
        String[] toolPartNames = {"pickaxe_head", "shovel_head", "axe_head", "hoe_head", "sword_blade", "armor_plate", "arrow_head"};
        fillPartsMap(toolParts, toolMaterials, toolPartNames);
        return toolParts;
    }

    public static final Map<String, RegistryObject<Item>> WEAPON_PARTS = addWeaponParts();

    public static Map<String, RegistryObject<Item>> addWeaponParts() {
        Map<String, RegistryObject<Item>> weaponParts = new HashMap<>();
        String[] weaponMaterials = {"iron", "steel", "silver"};
        String[] weaponPartNames = {"longsword_blade", "hammer_head", "hammer_head_raw", "long_axe_head"};
        fillPartsMap(weaponParts, weaponMaterials, weaponPartNames);
        return weaponParts;
    }

    private static void fillPartsMap(Map<String, RegistryObject<Item>> parts, String[] materials, String[] partNames) {
        for (String part : partNames) {
            for (String material : materials) {
                RegistryObject<Item> normalItem = ITEMS.register(material + "_" + part, () -> new Item(new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(16)));
                parts.put(material + "_" + part, normalItem);
                parts.put("heated_" + material + "_" + part, ITEMS.register("heated_" + material + "_" + part, () -> new HeatedItem(normalItem.get(), new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(16))));
            }
            RegistryObject<Item> normalItem = ITEMS.register("netherite_" + part, () -> new Item(new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(16).fireResistant()));
            parts.put("netherite_" + part, normalItem);
            parts.put("heated_netherite_" + part, ITEMS.register("heated_netherite_" + part, () -> new HeatedItem(normalItem.get(), new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(16).fireResistant())));
        }
    }

    //Tools
    public static final RegistryObject<Item> BRONZE_PICKAXE = registerTool("bronze_pickaxe", () -> new PickaxeItem(MWTiers.BRONZE, -1, -2.8f, new Item.Properties().tab(MedievalWorlds.toolsTab)));
    public static final RegistryObject<Item> BRONZE_AXE = registerTool("bronze_axe", () -> new AxeItem(MWTiers.BRONZE, 5, -3f, new Item.Properties().tab(MedievalWorlds.toolsTab)));
    public static final RegistryObject<Item> BRONZE_SHOVEL = registerTool("bronze_shovel", () -> new ShovelItem(MWTiers.BRONZE, 0, -3, new Item.Properties().tab(MedievalWorlds.toolsTab)));
    public static final RegistryObject<Item> BRONZE_HOE = registerTool("bronze_hoe", () -> new HoeItem(MWTiers.BRONZE, 0, -3f, new Item.Properties().tab(MedievalWorlds.toolsTab)));

    public static final RegistryObject<Item> STEEL_PICKAXE = registerTool("steel_pickaxe", () -> new PickaxeItem(MWTiers.STEEL, -1, -2.8f, new Item.Properties().tab(MedievalWorlds.toolsTab)));
    public static final RegistryObject<Item> STEEL_AXE = registerTool("steel_axe", () -> new AxeItem(MWTiers.STEEL, 5, -3.1f, new Item.Properties().tab(MedievalWorlds.toolsTab)));
    public static final RegistryObject<Item> STEEL_SHOVEL = registerTool("steel_shovel", () -> new ShovelItem(MWTiers.STEEL, 0, -3, new Item.Properties().tab(MedievalWorlds.toolsTab)));
    public static final RegistryObject<Item> STEEL_HOE = registerTool("steel_hoe", () -> new HoeItem(MWTiers.STEEL, 0, -3f, new Item.Properties().tab(MedievalWorlds.toolsTab)));

    public static final RegistryObject<Item> SILVER_PICKAXE = registerTool("silver_pickaxe", () -> new SilverPickaxe(MWTiers.SILVER, -1, -2.8f, new Item.Properties().tab(MedievalWorlds.toolsTab)));
    public static final RegistryObject<Item> SILVER_AXE = registerTool("silver_axe", () -> new SilverAxe(MWTiers.SILVER, 5, -3.1f, new Item.Properties().tab(MedievalWorlds.toolsTab)));
    public static final RegistryObject<Item> SILVER_SHOVEL = registerTool("silver_shovel", () -> new SilverShovel(MWTiers.SILVER, 0, -3, new Item.Properties().tab(MedievalWorlds.toolsTab)));
    public static final RegistryObject<Item> SILVER_HOE = registerTool("silver_hoe", () -> new SilverHoe(MWTiers.SILVER, 0, -3f, new Item.Properties().tab(MedievalWorlds.toolsTab)));

    public static final RegistryObject<Item> STONE_FORGE_HAMMER = registerTool("stone_forge_hammer", () -> new ForgeHammerItem(Tiers.STONE, new Item.Properties().tab(MedievalWorlds.toolsTab).durability(25)));
    public static final RegistryObject<Item> IRON_FORGE_HAMMER = registerTool("iron_forge_hammer", () -> new ForgeHammerItem(Tiers.IRON, new Item.Properties().tab(MedievalWorlds.toolsTab).durability(100)));
    public static final RegistryObject<Item> STEEL_FORGE_HAMMER = registerTool("steel_forge_hammer", () -> new ForgeHammerItem(MWTiers.STEEL, new Item.Properties().tab(MedievalWorlds.toolsTab).durability(500)));
    public static final RegistryObject<Item> NETHERITE_FORGE_HAMMER = registerTool("netherite_forge_hammer", () -> new ForgeHammerItem(Tiers.NETHERITE, new Item.Properties().tab(MedievalWorlds.toolsTab).fireResistant().durability(1000)));

    public static final RegistryObject<Item> FIRE_STARTER = registerTool("fire_starter", () -> new FlintAndSteelItem(new Item.Properties().durability(2).tab(MedievalWorlds.toolsTab)));

    //Weapons
    public static final RegistryObject<Item> BRONZE_SWORD = registerCombatItem("bronze_sword", () -> new SwordItem(MWTiers.BRONZE, 2, -2.8f, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> STEEL_SWORD = registerCombatItem("steel_sword", () -> new SwordItem(MWTiers.STEEL, 2, -2.8f, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> SILVER_SWORD = registerCombatItem("silver_sword", () -> new SilverSword(MWTiers.SILVER, 2, -2.4f, new Item.Properties().tab(MedievalWorlds.combatTab)));

    public static final RegistryObject<Item> IRON_HAMMER = registerCombatItem("iron_hammer", () -> new HammerItem(Tiers.IRON, 8, -3.4f, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> SILVER_HAMMER = registerCombatItem("silver_hammer", () -> new SilverHammer(MWTiers.SILVER, 8, -3.3f, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> STEEL_HAMMER = registerCombatItem("steel_hammer", () -> new HammerItem(MWTiers.STEEL, 8, -3.3f, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> NETHERITE_HAMMER = registerCombatItem("netherite_hammer", () -> new HammerItem(Tiers.NETHERITE, 8, -3.3f, new Item.Properties().tab(MedievalWorlds.combatTab).fireResistant()));

    public static final RegistryObject<Item> IRON_LONG_AXE = registerCombatItem("iron_long_axe", () -> new MWAxeWeapon(Tiers.IRON, 4, -3, 2, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> SILVER_LONG_AXE = registerCombatItem("silver_long_axe", () -> new SilverLongAxe(MWTiers.SILVER, 4, -3, 2, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> STEEL_LONG_AXE = registerCombatItem("steel_long_axe", () -> new MWAxeWeapon(MWTiers.STEEL, 4, -3, 2, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> NETHERITE_LONG_AXE = registerCombatItem("netherite_long_axe", () -> new MWAxeWeapon(Tiers.NETHERITE, 4, -3f, 2, new Item.Properties().tab(MedievalWorlds.combatTab).fireResistant()));

    public static final RegistryObject<Item> IRON_LONGSWORD = registerCombatItem("iron_longsword", () -> new MWSwordWeapon(Tiers.IRON, 6, -2.8f, 0.5f, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> SILVER_LONGSWORD = registerCombatItem("silver_longsword", () -> new SilverCustomSword(MWTiers.STEEL, 6, -2.8f, 0.5f, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> STEEL_LONGSWORD = registerCombatItem("steel_longsword", () -> new MWSwordWeapon(MWTiers.STEEL, 6, -2.8f, 0.5f, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> NETHERITE_LONGSWORD = registerCombatItem("netherite_longsword", () -> new MWSwordWeapon(Tiers.NETHERITE, 6, -2.7f, 0.5f, new Item.Properties().tab(MedievalWorlds.combatTab).fireResistant()));

    public static final RegistryObject<Item> IRON_DAGGER = registerCombatItem("iron_dagger", () -> new MWSwordWeapon(Tiers.IRON, 1, 0, -1.5f, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> SILVER_DAGGER = registerCombatItem("silver_dagger", () -> new SilverCustomSword(MWTiers.SILVER, 1, 0, -1.5f, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> STEEL_DAGGER = registerCombatItem("steel_dagger", () -> new MWSwordWeapon(MWTiers.STEEL, 1, 0, -1.5f, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> NETHERITE_DAGGER = registerCombatItem("netherite_dagger", () -> new MWSwordWeapon(Tiers.NETHERITE, 1, 0, -1.5f, new Item.Properties().tab(MedievalWorlds.combatTab).fireResistant()));

    //Armor
    public static final RegistryObject<Item> BRONZE_HELMET = registerCombatItem("bronze_helmet", () -> new ArmorItem(MWArmorMaterials.BRONZE_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> BRONZE_CHESTPLATE = registerCombatItem("bronze_chestplate", () -> new ArmorItem(MWArmorMaterials.BRONZE_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> BRONZE_LEGGINGS = registerCombatItem("bronze_leggings", () -> new ArmorItem(MWArmorMaterials.BRONZE_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> BRONZE_BOOTS = registerCombatItem("bronze_boots", () -> new ArmorItem(MWArmorMaterials.BRONZE_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.combatTab)));

    public static final RegistryObject<Item> IRON_KNIGHT_HELMET = registerCombatItem("iron_knight_helmet", () -> new KnightArmorHelmetItem(MWArmorMaterials.IRON_KNIGHT_MATERIAL, new Item.Properties().tab(MedievalWorlds.combatTab), "iron"));
    public static final RegistryObject<Item> IRON_KNIGHT_CHESTPLATE = registerCombatItem("iron_knight_chestplate", () -> new KnightArmorChestplateItem(MWArmorMaterials.IRON_KNIGHT_MATERIAL, new Item.Properties().tab(MedievalWorlds.combatTab), "iron"));
    public static final RegistryObject<Item> IRON_KNIGHT_LEGGINGS = registerCombatItem("iron_knight_leggings", () -> new KnightArmorLeggingsItem(MWArmorMaterials.IRON_KNIGHT_MATERIAL, new Item.Properties().tab(MedievalWorlds.combatTab), "iron"));
    public static final RegistryObject<Item> IRON_KNIGHT_BOOTS = registerCombatItem("iron_knight_boots", () -> new KnightArmorBootsItem(MWArmorMaterials.IRON_KNIGHT_MATERIAL, new Item.Properties().tab(MedievalWorlds.combatTab), "iron"));

    public static final RegistryObject<Item> SILVER_KNIGHT_HELMET = registerCombatItem("silver_knight_helmet", () -> new KnightArmorHelmetItem(MWArmorMaterials.SILVER_KNIGHT_MATERIAL, new Item.Properties().tab(MedievalWorlds.combatTab), "silver"));
    public static final RegistryObject<Item> SILVER_KNIGHT_CHESTPLATE = registerCombatItem("silver_knight_chestplate", () -> new KnightArmorChestplateItem(MWArmorMaterials.SILVER_KNIGHT_MATERIAL, new Item.Properties().tab(MedievalWorlds.combatTab), "silver"));
    public static final RegistryObject<Item> SILVER_KNIGHT_LEGGINGS = registerCombatItem("silver_knight_leggings", () -> new KnightArmorLeggingsItem(MWArmorMaterials.SILVER_KNIGHT_MATERIAL, new Item.Properties().tab(MedievalWorlds.combatTab), "silver"));
    public static final RegistryObject<Item> SILVER_KNIGHT_BOOTS = registerCombatItem("silver_knight_boots", () -> new KnightArmorBootsItem(MWArmorMaterials.SILVER_KNIGHT_MATERIAL, new Item.Properties().tab(MedievalWorlds.combatTab), "silver"));

    public static final RegistryObject<Item> STEEL_KNIGHT_HELMET = registerCombatItem("steel_knight_helmet", () -> new KnightArmorHelmetItem(MWArmorMaterials.STEEL_KNIGHT_MATERIAL, new Item.Properties().tab(MedievalWorlds.combatTab), "steel"));
    public static final RegistryObject<Item> STEEL_KNIGHT_CHESTPLATE = registerCombatItem("steel_knight_chestplate", () -> new KnightArmorChestplateItem(MWArmorMaterials.STEEL_KNIGHT_MATERIAL, new Item.Properties().tab(MedievalWorlds.combatTab), "steel"));
    public static final RegistryObject<Item> STEEL_KNIGHT_LEGGINGS = registerCombatItem("steel_knight_leggings", () -> new KnightArmorLeggingsItem(MWArmorMaterials.STEEL_KNIGHT_MATERIAL, new Item.Properties().tab(MedievalWorlds.combatTab), "steel"));
    public static final RegistryObject<Item> STEEL_KNIGHT_BOOTS = registerCombatItem("steel_knight_boots", () -> new KnightArmorBootsItem(MWArmorMaterials.STEEL_KNIGHT_MATERIAL, new Item.Properties().tab(MedievalWorlds.combatTab), "steel"));

    public static final RegistryObject<Item> NETHERITE_KNIGHT_HELMET = registerCombatItem("netherite_knight_helmet", () -> new KnightArmorHelmetItem(MWArmorMaterials.NETHERITE_KNIGHT_MATERIAL, new Item.Properties().tab(MedievalWorlds.combatTab).fireResistant(), "netherite"));
    public static final RegistryObject<Item> NETHERITE_KNIGHT_CHESTPLATE = registerCombatItem("netherite_knight_chestplate", () -> new KnightArmorChestplateItem(MWArmorMaterials.NETHERITE_KNIGHT_MATERIAL, new Item.Properties().tab(MedievalWorlds.combatTab).fireResistant(), "netherite"));
    public static final RegistryObject<Item> NETHERITE_KNIGHT_LEGGINGS = registerCombatItem("netherite_knight_leggings", () -> new KnightArmorLeggingsItem(MWArmorMaterials.NETHERITE_KNIGHT_MATERIAL, new Item.Properties().tab(MedievalWorlds.combatTab).fireResistant(), "netherite"));
    public static final RegistryObject<Item> NETHERITE_KNIGHT_BOOTS = registerCombatItem("netherite_knight_boots", () -> new KnightArmorBootsItem(MWArmorMaterials.NETHERITE_KNIGHT_MATERIAL, new Item.Properties().tab(MedievalWorlds.combatTab).fireResistant(), "netherite"));

    public static final RegistryObject<Item> SILVER_HELMET = registerCombatItem("silver_helmet", () -> new ArmorItem(MWArmorMaterials.SILVER_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> SILVER_CHESTPLATE = registerCombatItem("silver_chestplate", () -> new ArmorItem(MWArmorMaterials.SILVER_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> SILVER_LEGGINGS = registerCombatItem("silver_leggings", () -> new ArmorItem(MWArmorMaterials.SILVER_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> SILVER_BOOTS = registerCombatItem("silver_boots", () -> new ArmorItem(MWArmorMaterials.SILVER_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.combatTab)));

    public static final RegistryObject<Item> STEEL_HELMET = registerCombatItem("steel_helmet", () -> new ArmorItem(MWArmorMaterials.STEEL_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> STEEL_CHESTPLATE = registerCombatItem("steel_chestplate", () -> new ArmorItem(MWArmorMaterials.STEEL_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> STEEL_LEGGINGS = registerCombatItem("steel_leggings", () -> new ArmorItem(MWArmorMaterials.STEEL_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> STEEL_BOOTS = registerCombatItem("steel_boots", () -> new ArmorItem(MWArmorMaterials.STEEL_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.combatTab)));

    public static final RegistryObject<Item> SILVER_HORSE_ARMOR = registerCombatItem("silver_horse_armor", () -> new CustomHorseArmorItem(6, new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/horse/armor/horse_armor_netherite.png"), (new Item.Properties()).stacksTo(1).tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> STEEL_HORSE_ARMOR = registerCombatItem("steel_horse_armor", () -> new CustomHorseArmorItem(12, new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/horse/armor/horse_armor_steel.png"), (new Item.Properties()).stacksTo(1).tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> NETHERITE_HORSE_ARMOR = registerCombatItem("netherite_horse_armor", () -> new CustomHorseArmorItem(15, new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/horse/armor/horse_armor_netherite.png"), (new Item.Properties()).stacksTo(1).tab(MedievalWorlds.combatTab).fireResistant()));

    public static final RegistryObject<Item> COIF = registerCombatItem("coif", () -> new CoifHelmetItem(MWArmorMaterials.GAMBESON_MATERIAL, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> GAMBESON = registerCombatItem("gambeson", () -> new GambesonChestplateItem(MWArmorMaterials.GAMBESON_MATERIAL, new Item.Properties().tab(MedievalWorlds.combatTab)));

    //Block Items
    public static final RegistryObject<BlockItem> CHARCOAL_LOG = registerBlockItem("charcoal_log", () -> new BlockItem(MWBlocks.CHARCOAL_LOG.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> CHARCOAL_PLANKS = registerBlockItem("charcoal_planks", () -> new BlockItem(MWBlocks.CHARCOAL_PLANKS.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> STONE_ANVIL = registerBlockItem("stone_anvil", () -> new BlockItem(MWBlocks.STONE_ANVIL.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> STEEL_ANVIL = registerBlockItem("steel_anvil", () -> new BlockItem(MWBlocks.STEEL_ANVIL.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    public static final RegistryObject<BlockItem> STEEL_BLOCK = registerBlockItem("steel_block", () -> new BlockItem(MWBlocks.STEEL_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> SILVER_BLOCK = registerBlockItem("silver_block", () -> new BlockItem(MWBlocks.SILVER_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> BRONZE_BLOCK = registerBlockItem("bronze_block", () -> new BlockItem(MWBlocks.BRONZE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> TIN_BLOCK = registerBlockItem("tin_block", () -> new BlockItem(MWBlocks.TIN_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    public static final RegistryObject<BlockItem> SILVER_ORE = registerBlockItem("silver_ore", () -> new BlockItem(MWBlocks.SILVER_ORE.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> TIN_ORE = registerBlockItem("tin_ore", () -> new BlockItem(MWBlocks.TIN_ORE.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    public static final RegistryObject<BlockItem> DEEPSLATE_SILVER_ORE = registerBlockItem("deepslate_silver_ore", () -> new BlockItem(MWBlocks.DEEPSLATE_SILVER_ORE.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> DEEPSLATE_TIN_ORE = registerBlockItem("deepslate_tin_ore", () -> new BlockItem(MWBlocks.DEEPSLATE_TIN_ORE.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    public static final RegistryObject<BlockItem> RAW_SILVER_BLOCK = registerBlockItem("raw_silver_block", () -> new BlockItem(MWBlocks.RAW_SILVER_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> RAW_TIN_BLOCK = registerBlockItem("raw_tin_block", () -> new BlockItem(MWBlocks.RAW_TIN_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    public static final RegistryObject<BlockItem> ALLOY_FURNACE = registerBlockItem("alloy_furnace", () -> new BlockItem(MWBlocks.ALLOY_FURNACE.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> SLACK_TUB = registerBlockItem("slack_tub", () -> new BlockItem(MWBlocks.SLACK_TUB_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> SIMPLE_BLOOMERY = registerBlockItem("simple_bloomery", () -> new BlockItem(MWBlocks.SIMPLE_BLOOMERY.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    private static RegistryObject<BlockItem> registerBlockItem(String id, Supplier<BlockItem> itemSupplier) {
        RegistryObject<BlockItem> regObj = ITEMS.register(id, itemSupplier);
        blockItemRegistryOrder.add(regObj);
        return regObj;
    }

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

    public static List<BlockItem> getBlockItemTabOrder() {
        if(blockItemOrder == null) {
            blockItemOrder = blockItemRegistryOrder.stream().collect(ArrayList::new, (list, regObj) -> list.add(regObj.get()), ArrayList::addAll);
        }
        return blockItemOrder;
    }

    public static RegistryObject<Item> getEntry(ResourceLocation id) {
        return ITEMS.getEntries().stream().filter(ro -> ro.getId().equals(id)).findFirst().orElse(null);
    }

    private static <T extends Item> List<T> createOrderList(List<RegistryObject<T>> registries){
        return registries.stream().collect(ArrayList::new, (list, regObj) -> list.add(regObj.get()), ArrayList::addAll);
    }

}
