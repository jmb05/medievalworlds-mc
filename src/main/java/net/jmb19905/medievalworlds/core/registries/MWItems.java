package net.jmb19905.medievalworlds.core.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.item.*;
import net.jmb19905.medievalworlds.common.item.armor.*;
import net.jmb19905.medievalworlds.common.item.heated.HeatedIngotItem;
import net.jmb19905.medievalworlds.common.item.heated.HeatedToolPart;
import net.jmb19905.medievalworlds.common.item.lance.LanceItem;
import net.jmb19905.medievalworlds.common.item.tiers.MWArmorMaterials;
import net.jmb19905.medievalworlds.common.item.tiers.MWItemTiers;
import net.jmb19905.medievalworlds.util.ConfigHandler;
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

@SuppressWarnings({"unused", "unchecked"})
public class MWItems {

    private static List<? extends Item> toolItemOrder;
    private static List<? extends Item> materialItemOrder;
    private static List<? extends Item> combatItemOrder;

    private static final List<RegistryObject<Item>> toolItemRegistryOrder = new ArrayList<>();
    private static final List<RegistryObject<Item>> materialItemRegistryOrder = new ArrayList<>();
    private static final List<RegistryObject<Item>> combatItemRegistryOrder = new ArrayList<>();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MedievalWorlds.MOD_ID);

    //Materials
    public static final RegistryObject<Item> RAW_TIN = registerMaterial("raw_tin", () -> new Item(new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));
    public static final RegistryObject<Item> TIN_INGOT = registerMaterial("tin_ingot", () -> new Item(new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));
    public static final RegistryObject<Item> TIN_NUGGET = registerMaterial("tin_nugget", () -> new Item(new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));

    public static final RegistryObject<Item> BRONZE_INGOT = registerMaterial("bronze_ingot", () -> new Item(new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));
    public static final RegistryObject<Item> BRONZE_NUGGET = registerMaterial("bronze_nugget", () -> new Item(new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));

    public static final RegistryObject<Item> STEEL_INGOT = registerMaterial("steel_ingot", () -> new Item(new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));
    public static final RegistryObject<Item> STEEL_NUGGET = registerMaterial("steel_nugget", () -> new Item(new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));

    public static final RegistryObject<Item> RAW_SILVER = registerMaterial("raw_silver", () -> new Item(new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));
    public static final RegistryObject<Item> SILVER_INGOT = registerMaterial("silver_ingot", () -> new Item(new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));
    public static final RegistryObject<Item> SILVER_NUGGET = registerMaterial("silver_nugget", () -> new Item(new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));

    public static final RegistryObject<Item> RUBY = registerMaterial("ruby", () -> new Item(new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));

    public static final RegistryObject<HeatedIngotItem> HEATED_IRON_INGOT = registerMaterial("heated_iron_ingot", () -> new HeatedIngotItem(Items.IRON_INGOT, "iron", new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));
    public static final RegistryObject<HeatedIngotItem> HEATED_STEEL_INGOT = registerMaterial("heated_steel_ingot", () -> new HeatedIngotItem(MWItems.STEEL_INGOT.get(), "steel", new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));
    public static final RegistryObject<HeatedIngotItem> HEATED_COPPER_INGOT = registerMaterial("heated_copper_ingot", () -> new HeatedIngotItem(Items.COPPER_INGOT, "copper", new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));
    public static final RegistryObject<HeatedIngotItem> HEATED_TIN_INGOT = registerMaterial("heated_tin_ingot", () -> new HeatedIngotItem(MWItems.TIN_INGOT.get(), "tin", new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));
    public static final RegistryObject<HeatedIngotItem> HEATED_BRONZE_INGOT = registerMaterial("heated_bronze_ingot", () -> new HeatedIngotItem(MWItems.BRONZE_INGOT.get(), "bronze", new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));
    public static final RegistryObject<HeatedIngotItem> HEATED_SILVER_INGOT = registerMaterial("heated_silver_ingot", () -> new HeatedIngotItem(MWItems.SILVER_INGOT.get(), "silver", new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));
    public static final RegistryObject<HeatedIngotItem> HEATED_GOLD_INGOT = registerMaterial("heated_gold_ingot", () -> new HeatedIngotItem(Items.GOLD_INGOT, "gold", new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64)));
    public static final RegistryObject<HeatedIngotItem> HEATED_NETHERITE_INGOT = registerMaterial("heated_netherite_ingot", () -> new HeatedIngotItem(Items.NETHERITE_INGOT, "netherite", new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(64).fireResistant()));

    public static final Map<String, RegistryObject<Item>> TOOL_PARTS = addToolParts();

    public static Map<String, RegistryObject<Item>> addToolParts() {
        Map<String, RegistryObject<Item>> toolParts = new HashMap<>();
        String[] toolMaterials = {"iron", "steel", "bronze", "silver", "gold"};//excluding netherite,copper,bronze
        String[] toolPartNames = {"pickaxe_head", "shovel_head", "axe_head", "hoe_head", "sword_blade", "armor_plate"};
        for (String part : toolPartNames) {
            for(String material : toolMaterials) {
                RegistryObject<Item> normalItem = ITEMS.register(material + "_" + part, () -> new Item(new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(16)));
                toolParts.put(material + "_" + part, normalItem);
                toolParts.put("heated_" + material + "_" + part, ITEMS.register("heated_" + material + "_" + part, () -> new HeatedToolPart(normalItem.get(), material, part, new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(16))));
            }
            RegistryObject<Item> normalItem = ITEMS.register("netherite_" + part, () -> new Item(new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(16).fireResistant()));
            toolParts.put("netherite_" + part, normalItem);
            toolParts.put("heated_netherite_" + part, ITEMS.register("heated_netherite_" + part, () -> new HeatedToolPart(normalItem.get(), "netherite", part, new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(16).fireResistant())));
        }
        return toolParts;
    }

    public static final Map<String, RegistryObject<Item>> WEAPON_PARTS = addWeaponParts();

    public static Map<String, RegistryObject<Item>> addWeaponParts() {
        Map<String, RegistryObject<Item>> weaponParts = new HashMap<>();
        String[] weaponMaterials = {"iron", "steel", "silver"};
        String[] weaponPartNames = {"longsword_blade", "hammer_head", "hammer_head_raw", "battle_axe_head"};
        for (String part : weaponPartNames) {
            for (String material : weaponMaterials) {
                RegistryObject<Item> normalItem = ITEMS.register(material + "_" + part, () -> new Item(new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(16)));
                weaponParts.put(material + "_" + part, normalItem);
                weaponParts.put("heated_" + material + "_" + part, ITEMS.register("heated_" + material + "_" + part, () -> new HeatedToolPart(normalItem.get(), material, part, new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(16))));
            }
            RegistryObject<Item> normalItem = ITEMS.register("netherite_" + part, () -> new Item(new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(16).fireResistant()));
            weaponParts.put("netherite_" + part, normalItem);
            weaponParts.put("heated_netherite_" + part, ITEMS.register("heated_netherite_" + part, () -> new HeatedToolPart(normalItem.get(), "netherite", part, new Item.Properties().tab(MedievalWorlds.materialsTab).stacksTo(16).fireResistant())));
        }
        return weaponParts;
    }

    //Tools
    public static final RegistryObject<Item> BRONZE_PICKAXE = registerTool("bronze_pickaxe", () -> new PickaxeItem(MWItemTiers.BRONZE_ITEM_TIER, -1, -2.8f, new Item.Properties().tab(MedievalWorlds.toolsTab)));
    public static final RegistryObject<Item> BRONZE_AXE = registerTool("bronze_axe", () -> new AxeItem(MWItemTiers.BRONZE_ITEM_TIER, 5, -3f, new Item.Properties().tab(MedievalWorlds.toolsTab)));
    public static final RegistryObject<Item> BRONZE_SHOVEL = registerTool("bronze_shovel", () -> new ShovelItem(MWItemTiers.BRONZE_ITEM_TIER, 0, -3, new Item.Properties().tab(MedievalWorlds.toolsTab)));
    public static final RegistryObject<Item> BRONZE_HOE = registerTool("bronze_hoe", () -> new HoeItem(MWItemTiers.BRONZE_ITEM_TIER, 0, -3f, new Item.Properties().tab(MedievalWorlds.toolsTab)));

    public static final RegistryObject<Item> STEEL_PICKAXE = registerTool("steel_pickaxe", () -> new PickaxeItem(MWItemTiers.STEEL_ITEM_TIER, -1, -2.8f, new Item.Properties().tab(MedievalWorlds.toolsTab)));
    public static final RegistryObject<Item> STEEL_AXE = registerTool("steel_axe", () -> new AxeItem(MWItemTiers.STEEL_ITEM_TIER, 5, -3.1f, new Item.Properties().tab(MedievalWorlds.toolsTab)));
    public static final RegistryObject<Item> STEEL_SHOVEL = registerTool("steel_shovel", () -> new ShovelItem(MWItemTiers.STEEL_ITEM_TIER, 0, -3, new Item.Properties().tab(MedievalWorlds.toolsTab)));
    public static final RegistryObject<Item> STEEL_HOE = registerTool("steel_hoe", () -> new HoeItem(MWItemTiers.STEEL_ITEM_TIER, 0, -3f, new Item.Properties().tab(MedievalWorlds.toolsTab)));

    public static final RegistryObject<Item> SILVER_PICKAXE = registerTool("silver_pickaxe", () -> new PickaxeItem(MWItemTiers.SILVER_ITEM_TIER, -1, -2.8f, new Item.Properties().tab(MedievalWorlds.toolsTab)));
    public static final RegistryObject<Item> SILVER_AXE = registerTool("silver_axe", () -> new AxeItem(MWItemTiers.SILVER_ITEM_TIER, 5, -3.1f, new Item.Properties().tab(MedievalWorlds.toolsTab)));
    public static final RegistryObject<Item> SILVER_SHOVEL = registerTool("silver_shovel", () -> new ShovelItem(MWItemTiers.SILVER_ITEM_TIER, 0, -3, new Item.Properties().tab(MedievalWorlds.toolsTab)));
    public static final RegistryObject<Item> SILVER_HOE = registerTool("silver_hoe", () -> new HoeItem(MWItemTiers.SILVER_ITEM_TIER, 0, -3f, new Item.Properties().tab(MedievalWorlds.toolsTab)));

    public static final RegistryObject<Item> STONE_FORGE_HAMMER = registerTool("stone_forge_hammer", () -> new ForgeHammerItem(Tiers.STONE, new Item.Properties().tab(MedievalWorlds.toolsTab).durability(25)));
    public static final RegistryObject<Item> IRON_FORGE_HAMMER = registerTool("iron_forge_hammer", () -> new ForgeHammerItem(Tiers.IRON, new Item.Properties().tab(MedievalWorlds.toolsTab).durability(100)));
    public static final RegistryObject<Item> STEEL_FORGE_HAMMER = registerTool("steel_forge_hammer", () -> new ForgeHammerItem(MWItemTiers.STEEL_ITEM_TIER, new Item.Properties().tab(MedievalWorlds.toolsTab).durability(500)));
    public static final RegistryObject<Item> NETHERITE_FORGE_HAMMER = registerTool("netherite_forge_hammer", () -> new ForgeHammerItem(Tiers.NETHERITE, new Item.Properties().tab(MedievalWorlds.toolsTab).fireResistant().durability(1000)));

    public static final RegistryObject<Item> FIRE_STARTER = registerTool("fire_starter", () -> new FlintAndSteelItem(new Item.Properties().durability(2).tab(MedievalWorlds.toolsTab)));

    //Weapons
    public static final RegistryObject<Item> BRONZE_SWORD = registerCombatItem("bronze_sword", () -> new SwordItem(MWItemTiers.TIN_ITEM_TIER, 2, -2.8f, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> STEEL_SWORD = registerCombatItem("steel_sword", () -> new SwordItem(MWItemTiers.TIN_ITEM_TIER, 3, -2.8f, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> SILVER_SWORD = registerCombatItem("silver_sword", () -> new SilverSword(MWItemTiers.TIN_ITEM_TIER, 2, -2.4f, new Item.Properties().tab(MedievalWorlds.combatTab)));

    public static final RegistryObject<Item> IRON_HAMMER = registerCombatItem("iron_hammer", () -> new HammerItem(MWItemTiers.IRON_BLOCK_ITEM_TIER, 10, -3.4f, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> SILVER_HAMMER = registerCombatItem("silver_hammer", () -> new HammerItem(MWItemTiers.SILVER_BLOCK_ITEM_TIER, 10, -3.3f, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> STEEL_HAMMER = registerCombatItem("steel_hammer", () -> new HammerItem(MWItemTiers.STEEL_BLOCK_ITEM_TIER, 10, -3.3f, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> NETHERITE_HAMMER = registerCombatItem("netherite_hammer", () -> new HammerItem(MWItemTiers.NETHERITE_BLOCK_ITEM_TIER, 10, -3.3f, new Item.Properties().tab(MedievalWorlds.combatTab).fireResistant()));

    public static final RegistryObject<Item> IRON_BATTLE_AXE = registerCombatItem("iron_battle_axe", () -> new AxeItem(MWItemTiers.IRON_BLOCK_ITEM_TIER, 8, -3, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> SILVER_BATTLE_AXE = registerCombatItem("silver_battle_axe", () -> new AxeItem(MWItemTiers.SILVER_BLOCK_ITEM_TIER, 8, -3, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> STEEL_BATTLE_AXE = registerCombatItem("steel_battle_axe", () -> new AxeItem(MWItemTiers.STEEL_BLOCK_ITEM_TIER, 8, -3, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> NETHERITE_BATTLE_AXE = registerCombatItem("netherite_battle_axe", () -> new AxeItem(MWItemTiers.NETHERITE_BLOCK_ITEM_TIER, 8, -3f, new Item.Properties().tab(MedievalWorlds.combatTab).fireResistant()));

    public static final RegistryObject<Item> IRON_LONGSWORD = registerCombatItem("iron_longsword", () -> new SwordItem(MWItemTiers.IRON_BLOCK_ITEM_TIER, (int) (ConfigHandler.COMMON.ironLongswordAttackDamage.get() - MWItemTiers.IRON_BLOCK_ITEM_TIER.getAttackDamageBonus()) - 1, -2.8f, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> SILVER_LONGSWORD = registerCombatItem("silver_longsword", () -> new SwordItem(MWItemTiers.STEEL_BLOCK_ITEM_TIER, (int) (ConfigHandler.COMMON.silverLongswordAttackDamage.get() - MWItemTiers.SILVER_BLOCK_ITEM_TIER.getAttackDamageBonus()) - 2, -2.8f, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> STEEL_LONGSWORD = registerCombatItem("steel_longsword", () -> new SwordItem(MWItemTiers.STEEL_BLOCK_ITEM_TIER, (int) (ConfigHandler.COMMON.steelLongswordAttackDamage.get() - MWItemTiers.IRON_BLOCK_ITEM_TIER.getAttackDamageBonus()) - 1, -2.8f, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> NETHERITE_LONGSWORD = registerCombatItem("netherite_longsword", () -> new SwordItem(MWItemTiers.NETHERITE_BLOCK_ITEM_TIER, (int) (ConfigHandler.COMMON.netheriteLongswordAttackDamage.get() - MWItemTiers.NETHERITE_BLOCK_ITEM_TIER.getAttackDamageBonus()) - 1, -2.7f, new Item.Properties().tab(MedievalWorlds.combatTab).fireResistant()));

    public static final RegistryObject<Item> IRON_DAGGER = registerCombatItem("iron_dagger", () -> new SwordItem(Tiers.IRON, 0, 0, new Item.Properties().durability(800).tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> SILVER_DAGGER = registerCombatItem("silver_dagger", () -> new SwordItem(MWItemTiers.SILVER_ITEM_TIER, -1, 0, new Item.Properties().durability(600).tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> STEEL_DAGGER = registerCombatItem("steel_dagger", () -> new SwordItem(MWItemTiers.STEEL_ITEM_TIER, 0, 0, new Item.Properties().durability(1000).tab(MedievalWorlds.combatTab)));

    public static final RegistryObject<Item> LONGBOW = registerCombatItem("longbow", () -> new LongbowItem(new Item.Properties().tab(MedievalWorlds.combatTab).durability(1200)));

    public static final RegistryObject<Item> IRON_LANCE = registerCombatItem("iron_lance", () -> new LanceItem(2.5f, 20, new Item.Properties().durability(800).tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> SILVER_LANCE = registerCombatItem("silver_lance", () -> new LanceItem(2.75f, 15, new Item.Properties().durability(600).tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> STEEL_LANCE = registerCombatItem("steel_lance", () -> new LanceItem(3f, 25, new Item.Properties().durability(1000).tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> NETHERITE_LANCE = registerCombatItem("netherite_lance", () -> new LanceItem(3.5f, 30, new Item.Properties().durability(2000).tab(MedievalWorlds.combatTab).fireResistant()));

    public static final RegistryObject<SpearItem> IRON_SPEAR = registerCombatItem("iron_spear", () -> new SpearItem(6, 2, new Item.Properties().tab(MedievalWorlds.combatTab).stacksTo(1).durability(600)));

    //Armor
    public static final RegistryObject<Item> BRONZE_HELMET = registerCombatItem("bronze_helmet", () -> new ArmorItem(MWArmorMaterials.BRONZE_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> BRONZE_CHESTPLATE = registerCombatItem("bronze_chestplate", () -> new ArmorItem(MWArmorMaterials.BRONZE_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> BRONZE_LEGGINGS = registerCombatItem("bronze_leggings", () -> new ArmorItem(MWArmorMaterials.BRONZE_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> BRONZE_BOOTS = registerCombatItem("bronze_boots", () -> new ArmorItem(MWArmorMaterials.BRONZE_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.combatTab)));

    public static final RegistryObject<Item> WARRIOR_HELMET = registerCombatItem("warrior_helmet", () -> new WarriorArmorItem(MWArmorMaterials.WARRIOR_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> WARRIOR_CHESTPLATE = registerCombatItem("warrior_chestplate", () -> new WarriorArmorItem(MWArmorMaterials.WARRIOR_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> WARRIOR_LEGGINGS = registerCombatItem("warrior_leggings", () -> new WarriorArmorItem(MWArmorMaterials.WARRIOR_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> WARRIOR_BOOTS = registerCombatItem("warrior_boots", () -> new WarriorArmorItem(MWArmorMaterials.WARRIOR_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.combatTab)));

    public static final RegistryObject<Item> IRON_KNIGHT_HELMET = registerCombatItem("iron_knight_helmet", () -> new KnightArmorHelmetItem(MWArmorMaterials.IRON_KNIGHT_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.combatTab), "iron"));
    public static final RegistryObject<Item> IRON_KNIGHT_CHESTPLATE = registerCombatItem("iron_knight_chestplate", () -> new KnightArmorChestplateItem(MWArmorMaterials.IRON_KNIGHT_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.combatTab), "iron"));
    public static final RegistryObject<Item> IRON_KNIGHT_LEGGINGS = registerCombatItem("iron_knight_leggings", () -> new KnightArmorLeggingsItem(MWArmorMaterials.IRON_KNIGHT_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.combatTab), "iron"));
    public static final RegistryObject<Item> IRON_KNIGHT_BOOTS = registerCombatItem("iron_knight_boots", () -> new KnightArmorBootsItem(MWArmorMaterials.IRON_KNIGHT_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.combatTab), "iron"));

    public static final RegistryObject<Item> SILVER_KNIGHT_HELMET = registerCombatItem("silver_knight_helmet", () -> new KnightArmorHelmetItem(MWArmorMaterials.SILVER_KNIGHT_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.combatTab), "silver"));
    public static final RegistryObject<Item> SILVER_KNIGHT_CHESTPLATE = registerCombatItem("silver_knight_chestplate", () -> new KnightArmorChestplateItem(MWArmorMaterials.SILVER_KNIGHT_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.combatTab), "silver"));
    public static final RegistryObject<Item> SILVER_KNIGHT_LEGGINGS = registerCombatItem("silver_knight_leggings", () -> new KnightArmorLeggingsItem(MWArmorMaterials.SILVER_KNIGHT_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.combatTab), "silver"));
    public static final RegistryObject<Item> SILVER_KNIGHT_BOOTS = registerCombatItem("silver_knight_boots", () -> new KnightArmorBootsItem(MWArmorMaterials.SILVER_KNIGHT_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.combatTab), "silver"));

    public static final RegistryObject<Item> STEEL_KNIGHT_HELMET = registerCombatItem("steel_knight_helmet", () -> new KnightArmorHelmetItem(MWArmorMaterials.STEEL_KNIGHT_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.combatTab), "steel"));
    public static final RegistryObject<Item> STEEL_KNIGHT_CHESTPLATE = registerCombatItem("steel_knight_chestplate", () -> new KnightArmorChestplateItem(MWArmorMaterials.STEEL_KNIGHT_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.combatTab), "steel"));
    public static final RegistryObject<Item> STEEL_KNIGHT_LEGGINGS = registerCombatItem("steel_knight_leggings", () -> new KnightArmorLeggingsItem(MWArmorMaterials.STEEL_KNIGHT_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.combatTab), "steel"));
    public static final RegistryObject<Item> STEEL_KNIGHT_BOOTS = registerCombatItem("steel_knight_boots", () -> new KnightArmorBootsItem(MWArmorMaterials.STEEL_KNIGHT_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.combatTab), "steel"));

    public static final RegistryObject<Item> NETHERITE_KNIGHT_HELMET = registerCombatItem("netherite_knight_helmet", () -> new KnightArmorHelmetItem(MWArmorMaterials.NETHERITE_KNIGHT_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.combatTab).fireResistant(), "netherite"));
    public static final RegistryObject<Item> NETHERITE_KNIGHT_CHESTPLATE = registerCombatItem("netherite_knight_chestplate", () -> new KnightArmorChestplateItem(MWArmorMaterials.NETHERITE_KNIGHT_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.combatTab).fireResistant(), "netherite"));
    public static final RegistryObject<Item> NETHERITE_KNIGHT_LEGGINGS = registerCombatItem("netherite_knight_leggings", () -> new KnightArmorLeggingsItem(MWArmorMaterials.NETHERITE_KNIGHT_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.combatTab).fireResistant(), "netherite"));
    public static final RegistryObject<Item> NETHERITE_KNIGHT_BOOTS = registerCombatItem("netherite_knight_boots", () -> new KnightArmorBootsItem(MWArmorMaterials.NETHERITE_KNIGHT_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.combatTab).fireResistant(), "netherite"));

    public static final RegistryObject<Item> SILVER_HELMET = registerCombatItem("silver_helmet", () -> new ArmorItem(MWArmorMaterials.SILVER_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> SILVER_CHESTPLATE = registerCombatItem("silver_chestplate", () -> new ArmorItem(MWArmorMaterials.SILVER_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> SILVER_LEGGINGS = registerCombatItem("silver_leggings", () -> new ArmorItem(MWArmorMaterials.SILVER_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> SILVER_BOOTS = registerCombatItem("silver_boots", () -> new ArmorItem(MWArmorMaterials.SILVER_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.combatTab)));

    public static final RegistryObject<Item> STEEL_HELMET = registerCombatItem("steel_helmet", () -> new ArmorItem(MWArmorMaterials.STEEL_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> STEEL_CHESTPLATE = registerCombatItem("steel_chestplate", () -> new ArmorItem(MWArmorMaterials.STEEL_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> STEEL_LEGGINGS = registerCombatItem("steel_leggings", () -> new ArmorItem(MWArmorMaterials.STEEL_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> STEEL_BOOTS = registerCombatItem("steel_boots", () -> new ArmorItem(MWArmorMaterials.STEEL_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.combatTab)));

    public static final RegistryObject<Item> GAMBESON_CHESTPLATE = registerCombatItem("gambeson_chestplate", () -> new GambesonChestplateItem(new Item.Properties().tab(MedievalWorlds.combatTab)));

    public static final RegistryObject<Item> SILVER_HORSE_ARMOR = registerCombatItem("silver_horse_armor", () -> new CustomHorseArmorItem(6, new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/horse/armor/horse_armor_netherite.png"), (new Item.Properties()).stacksTo(1).tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> STEEL_HORSE_ARMOR = registerCombatItem("steel_horse_armor", () -> new CustomHorseArmorItem(12, new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/horse/armor/horse_armor_steel.png"), (new Item.Properties()).stacksTo(1).tab(MedievalWorlds.combatTab)));
    public static final RegistryObject<Item> NETHERITE_HORSE_ARMOR = registerCombatItem("netherite_horse_armor", () -> new CustomHorseArmorItem(15, new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/horse/armor/horse_armor_netherite.png"), (new Item.Properties()).stacksTo(1).tab(MedievalWorlds.combatTab).fireResistant()));

    public static final RegistryObject<Item> CROWN = registerCombatItem("crown", () -> new CrownItem(ArmorMaterials.GOLD, new Item.Properties().tab(MedievalWorlds.combatTab)));

    public static final RegistryObject<Item> ROUND_SHIELD = registerCombatItem("round_shield", () -> new CustomShield(new Item.Properties().tab(MedievalWorlds.combatTab).stacksTo(1).durability(200)));

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