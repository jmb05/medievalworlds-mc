package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.item.*;
import net.jmb19905.medievalworlds.common.item.armor.*;
import net.jmb19905.medievalworlds.common.item.tiers.*;
import net.jmb19905.medievalworlds.util.ConfigHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class ItemRegistryHandler {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MedievalWorlds.MOD_ID);

    //Materials

    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> TIN_NUGGET = ITEMS.register("tin_nugget", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> BRONZE_NUGGET = ITEMS.register("bronze_nugget", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<Item> LONG_STICK = ITEMS.register("long_stick", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(64)));

    public static final RegistryObject<Item> LONG_STRING = ITEMS.register("long_string", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(64)));

    //Tools
    public static final RegistryObject<Item> TIN_PICKAXE = ITEMS.register("tin_pickaxe", () -> new PickaxeItem(MWItemTiers.TIN_ITEM_TIER, -1, -2.8f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> TIN_AXE = ITEMS.register("tin_axe", () -> new AxeItem(MWItemTiers.TIN_ITEM_TIER, 4, -3f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> TIN_SHOVEL = ITEMS.register("tin_shovel", () -> new ShovelItem(MWItemTiers.TIN_ITEM_TIER, 0, -3, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> TIN_HOE = ITEMS.register("tin_hoe", () -> new HoeItem(MWItemTiers.TIN_ITEM_TIER, 0, -3, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> COPPER_PICKAXE = ITEMS.register("copper_pickaxe", () -> new PickaxeItem(MWItemTiers.COPPER_ITEM_TIER, -1, -2.8f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> COPPER_AXE = ITEMS.register("copper_axe", () -> new AxeItem(MWItemTiers.COPPER_ITEM_TIER, 4, -3f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel", () -> new ShovelItem(MWItemTiers.COPPER_ITEM_TIER, 0, -3, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("copper_hoe", () -> new HoeItem(MWItemTiers.COPPER_ITEM_TIER, 0, -3f, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> BRONZE_PICKAXE = ITEMS.register("bronze_pickaxe", () -> new PickaxeItem(MWItemTiers.BRONZE_ITEM_TIER, -1, -2.8f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> BRONZE_AXE = ITEMS.register("bronze_axe", () -> new AxeItem(MWItemTiers.BRONZE_ITEM_TIER, 5, -3f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> BRONZE_SHOVEL = ITEMS.register("bronze_shovel", () -> new ShovelItem(MWItemTiers.BRONZE_ITEM_TIER, 0, -3, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> BRONZE_HOE = ITEMS.register("bronze_hoe", () -> new HoeItem(MWItemTiers.BRONZE_ITEM_TIER, 0, -3f, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> STEEL_PICKAXE = ITEMS.register("steel_pickaxe", () -> new PickaxeItem(MWItemTiers.STEEL_ITEM_TIER, -1, -2.8f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_AXE = ITEMS.register("steel_axe", () -> new AxeItem(MWItemTiers.STEEL_ITEM_TIER, 5, -3.1f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_SHOVEL = ITEMS.register("steel_shovel", () -> new ShovelItem(MWItemTiers.STEEL_ITEM_TIER, 0, -3, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_HOE = ITEMS.register("steel_hoe", () -> new HoeItem(MWItemTiers.STEEL_ITEM_TIER, 0, -3f, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> SILVER_PICKAXE = ITEMS.register("silver_pickaxe", () -> new PickaxeItem(MWItemTiers.SILVER_ITEM_TIER, -1, -2.8f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_AXE = ITEMS.register("silver_axe", () -> new AxeItem(MWItemTiers.SILVER_ITEM_TIER, 5, -3.1f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_SHOVEL = ITEMS.register("silver_shovel", () -> new ShovelItem(MWItemTiers.SILVER_ITEM_TIER, 0, -3, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_HOE = ITEMS.register("silver_hoe", () -> new HoeItem(MWItemTiers.SILVER_ITEM_TIER, 0, -3f, new Item.Properties().tab(MedievalWorlds.tab)));

    //Weapons
    public static final RegistryObject<Item> TIN_SWORD = ITEMS.register("tin_sword", () -> new SwordItem(MWItemTiers.TIN_ITEM_TIER, 1, -2.5f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword", () -> new SwordItem(MWItemTiers.TIN_ITEM_TIER, 1, -2.2f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> BRONZE_SWORD = ITEMS.register("bronze_sword", () -> new SwordItem(MWItemTiers.TIN_ITEM_TIER, 2, -2.8f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_SWORD = ITEMS.register("steel_sword", () -> new SwordItem(MWItemTiers.TIN_ITEM_TIER, 3, -2.8f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_SWORD = ITEMS.register("silver_sword", () -> new SilverSword(MWItemTiers.TIN_ITEM_TIER, 2, -2.4f, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer", () -> new HammerItem(MWItemTiers.IRON_BLOCK_ITEM_TIER, 10, -3.4f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_HAMMER = ITEMS.register("silver_hammer", () -> new HammerItem(MWItemTiers.SILVER_BLOCK_ITEM_TIER, 10, -3.3f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_HAMMER = ITEMS.register("steel_hammer", () -> new HammerItem(MWItemTiers.STEEL_BLOCK_ITEM_TIER, 10, -3.3f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> DIAMOND_HAMMER = ITEMS.register("diamond_hammer", () -> new HammerItem(MWItemTiers.DIAMOND_BLOCK_ITEM_TIER, 10, -3.2f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> NETHERITE_HAMMER = ITEMS.register("netherite_hammer", () -> new HammerItem(MWItemTiers.NETHERITE_BLOCK_ITEM_TIER, 10, -3.3f, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> IRON_BATTLE_AXE = ITEMS.register("iron_battle_axe", () -> new AxeItem(MWItemTiers.IRON_BLOCK_ITEM_TIER, 8, -3, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_BATTLE_AXE = ITEMS.register("silver_battle_axe", () -> new AxeItem(MWItemTiers.SILVER_BLOCK_ITEM_TIER, 8, -3, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_BATTLE_AXE = ITEMS.register("steel_battle_axe", () -> new AxeItem(MWItemTiers.STEEL_BLOCK_ITEM_TIER, 8, -3, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> DIAMOND_BATTLE_AXE = ITEMS.register("diamond_battle_axe", () -> new AxeItem(MWItemTiers.DIAMOND_BLOCK_ITEM_TIER, 8, -2.9f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> NETHERITE_BATTLE_AXE = ITEMS.register("netherite_battle_axe", () -> new AxeItem(MWItemTiers.NETHERITE_BLOCK_ITEM_TIER, 8, -3f, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> IRON_LONGSWORD = ITEMS.register("iron_longsword", () -> new SwordItem(MWItemTiers.IRON_BLOCK_ITEM_TIER, (int) (ConfigHandler.COMMON.ironLongswordAttackDamage.get() - MWItemTiers.IRON_BLOCK_ITEM_TIER.getAttackDamageBonus()) - 1, -2.8f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_LONGSWORD = ITEMS.register("silver_longsword", () -> new SwordItem(MWItemTiers.STEEL_BLOCK_ITEM_TIER, (int) (ConfigHandler.COMMON.silverLongswordAttackDamage.get() - MWItemTiers.SILVER_BLOCK_ITEM_TIER.getAttackDamageBonus()) - 2, -2.8f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_LONGSWORD = ITEMS.register("steel_longsword", () -> new SwordItem(MWItemTiers.STEEL_BLOCK_ITEM_TIER, (int) (ConfigHandler.COMMON.steelLongswordAttackDamage.get() - MWItemTiers.IRON_BLOCK_ITEM_TIER.getAttackDamageBonus()) - 1, -2.8f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> DIAMOND_LONGSWORD = ITEMS.register("diamond_longsword", () -> new SwordItem(MWItemTiers.DIAMOND_BLOCK_ITEM_TIER, (int) (ConfigHandler.COMMON.diamondLongswordAttackDamage.get() - MWItemTiers.DIAMOND_BLOCK_ITEM_TIER.getAttackDamageBonus()) - 1, -2.7f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> NETHERITE_LONGSWORD = ITEMS.register("netherite_longsword", () -> new SwordItem(MWItemTiers.NETHERITE_BLOCK_ITEM_TIER, (int) (ConfigHandler.COMMON.netheriteLongswordAttackDamage.get() - MWItemTiers.NETHERITE_BLOCK_ITEM_TIER.getAttackDamageBonus()) - 1, -2.7f, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> IRON_DAGGER = ITEMS.register("iron_dagger", () -> new SwordItem(Tiers.IRON, 0, 0, new Item.Properties().durability(800).tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_DAGGER = ITEMS.register("silver_dagger", () -> new SwordItem(MWItemTiers.SILVER_ITEM_TIER, -1, 0, new Item.Properties().durability(600).tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_DAGGER = ITEMS.register("steel_dagger", () -> new SwordItem(MWItemTiers.STEEL_ITEM_TIER, 0, 0, new Item.Properties().durability(1000).tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> DIAMOND_DAGGER = ITEMS.register("diamond_dagger", () -> new SwordItem(Tiers.DIAMOND, 0, 0, new Item.Properties().durability(1500).tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> LONGBOW = ITEMS.register("longbow", () -> new LongbowItem(new Item.Properties().tab(MedievalWorlds.tab).durability(1200)));

    public static final RegistryObject<Item> IRON_LANCE = ITEMS.register("iron_lance", () -> new LanceItem(2.5f, 20, new Item.Properties().durability(800).tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_LANCE = ITEMS.register("silver_lance", () -> new LanceItem(2.75f, 15, new Item.Properties().durability(600).tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_LANCE = ITEMS.register("steel_lance", () -> new LanceItem(3f, 25, new Item.Properties().durability(1000).tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> DIAMOND_LANCE = ITEMS.register("diamond_lance", () -> new LanceItem(3.25f, 20, new Item.Properties().durability(1500).tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> NETHERITE_LANCE = ITEMS.register("netherite_lance", () -> new LanceItem(3.5f, 30, new Item.Properties().durability(2000).tab(MedievalWorlds.tab)));

    //TODO: add spears

    //Armor
    public static final RegistryObject<Item> COPPER_HELMET = ITEMS.register("copper_helmet", () -> new ArmorItem(ModArmorMaterials.COPPER_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate", () -> new ArmorItem(ModArmorMaterials.COPPER_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> COPPER_LEGGINGS = ITEMS.register("copper_leggings", () -> new ArmorItem(ModArmorMaterials.COPPER_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> COPPER_BOOTS = ITEMS.register("copper_boots", () -> new ArmorItem(ModArmorMaterials.COPPER_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> TIN_HELMET = ITEMS.register("tin_helmet", () -> new ArmorItem(ModArmorMaterials.TIN_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> TIN_CHESTPLATE = ITEMS.register("tin_chestplate", () -> new ArmorItem(ModArmorMaterials.TIN_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> TIN_LEGGINGS = ITEMS.register("tin_leggings", () -> new ArmorItem(ModArmorMaterials.TIN_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> TIN_BOOTS = ITEMS.register("tin_boots", () -> new ArmorItem(ModArmorMaterials.TIN_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> BRONZE_HELMET = ITEMS.register("bronze_helmet", () -> new ArmorItem(ModArmorMaterials.BRONZE_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> BRONZE_CHESTPLATE = ITEMS.register("bronze_chestplate", () -> new ArmorItem(ModArmorMaterials.BRONZE_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> BRONZE_LEGGINGS = ITEMS.register("bronze_leggings", () -> new ArmorItem(ModArmorMaterials.BRONZE_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> BRONZE_BOOTS = ITEMS.register("bronze_boots", () -> new ArmorItem(ModArmorMaterials.BRONZE_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> WARRIOR_HELMET = ITEMS.register("warrior_helmet", () -> new WarriorArmorItem(ModArmorMaterials.WARRIOR_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> WARRIOR_CHESTPLATE = ITEMS.register("warrior_chestplate", () -> new WarriorArmorItem(ModArmorMaterials.WARRIOR_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> WARRIOR_LEGGINGS = ITEMS.register("warrior_leggings", () -> new WarriorArmorItem(ModArmorMaterials.WARRIOR_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> WARRIOR_BOOTS = ITEMS.register("warrior_boots", () -> new WarriorArmorItem(ModArmorMaterials.WARRIOR_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> IRON_KNIGHT_HELMET = ITEMS.register("iron_knight_helmet", () -> new KnightArmorHelmetItem(ModArmorMaterials.IRON_KNIGHT_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab), "iron"));
    public static final RegistryObject<Item> IRON_KNIGHT_CHESTPLATE = ITEMS.register("iron_knight_chestplate", () -> new KnightArmorChestplateItem(ModArmorMaterials.IRON_KNIGHT_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab), "iron"));
    public static final RegistryObject<Item> IRON_KNIGHT_LEGGINGS = ITEMS.register("iron_knight_leggings", () -> new KnightArmorLeggingsItem(ModArmorMaterials.IRON_KNIGHT_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab), "iron"));
    public static final RegistryObject<Item> IRON_KNIGHT_BOOTS = ITEMS.register("iron_knight_boots", () -> new KnightArmorBootsItem(ModArmorMaterials.IRON_KNIGHT_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab), "iron"));

    public static final RegistryObject<Item> SILVER_KNIGHT_HELMET = ITEMS.register("silver_knight_helmet", () -> new KnightArmorHelmetItem(ModArmorMaterials.SILVER_KNIGHT_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab), "silver"));
    public static final RegistryObject<Item> SILVER_KNIGHT_CHESTPLATE = ITEMS.register("silver_knight_chestplate", () -> new KnightArmorChestplateItem(ModArmorMaterials.SILVER_KNIGHT_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab), "silver"));
    public static final RegistryObject<Item> SILVER_KNIGHT_LEGGINGS = ITEMS.register("silver_knight_leggings", () -> new KnightArmorLeggingsItem(ModArmorMaterials.SILVER_KNIGHT_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab), "silver"));
    public static final RegistryObject<Item> SILVER_KNIGHT_BOOTS = ITEMS.register("silver_knight_boots", () -> new KnightArmorBootsItem(ModArmorMaterials.SILVER_KNIGHT_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab), "silver"));

    public static final RegistryObject<Item> STEEL_KNIGHT_HELMET = ITEMS.register("steel_knight_helmet", () -> new KnightArmorHelmetItem(ModArmorMaterials.STEEL_KNIGHT_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab), "steel"));
    public static final RegistryObject<Item> STEEL_KNIGHT_CHESTPLATE = ITEMS.register("steel_knight_chestplate", () -> new KnightArmorChestplateItem(ModArmorMaterials.STEEL_KNIGHT_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab), "steel"));
    public static final RegistryObject<Item> STEEL_KNIGHT_LEGGINGS = ITEMS.register("steel_knight_leggings", () -> new KnightArmorLeggingsItem(ModArmorMaterials.STEEL_KNIGHT_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab), "steel"));
    public static final RegistryObject<Item> STEEL_KNIGHT_BOOTS = ITEMS.register("steel_knight_boots", () -> new KnightArmorBootsItem(ModArmorMaterials.STEEL_KNIGHT_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab), "steel"));

    public static final RegistryObject<Item> DIAMOND_KNIGHT_HELMET = ITEMS.register("diamond_knight_helmet", () -> new KnightArmorHelmetItem(ModArmorMaterials.DIAMOND_KNIGHT_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab), "diamond"));
    public static final RegistryObject<Item> DIAMOND_KNIGHT_CHESTPLATE = ITEMS.register("diamond_knight_chestplate", () -> new KnightArmorChestplateItem(ModArmorMaterials.DIAMOND_KNIGHT_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab), "diamond"));
    public static final RegistryObject<Item> DIAMOND_KNIGHT_LEGGINGS = ITEMS.register("diamond_knight_leggings", () -> new KnightArmorLeggingsItem(ModArmorMaterials.DIAMOND_KNIGHT_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab), "diamond"));
    public static final RegistryObject<Item> DIAMOND_KNIGHT_BOOTS = ITEMS.register("diamond_knight_boots", () -> new KnightArmorBootsItem(ModArmorMaterials.DIAMOND_KNIGHT_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab), "diamond"));

    public static final RegistryObject<Item> NETHERITE_KNIGHT_HELMET = ITEMS.register("netherite_knight_helmet", () -> new KnightArmorHelmetItem(ModArmorMaterials.NETHERITE_KNIGHT_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab), "netherite"));
    public static final RegistryObject<Item> NETHERITE_KNIGHT_CHESTPLATE = ITEMS.register("netherite_knight_chestplate", () -> new KnightArmorChestplateItem(ModArmorMaterials.NETHERITE_KNIGHT_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab), "netherite"));
    public static final RegistryObject<Item> NETHERITE_KNIGHT_LEGGINGS = ITEMS.register("netherite_knight_leggings", () -> new KnightArmorLeggingsItem(ModArmorMaterials.NETHERITE_KNIGHT_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab), "netherite"));
    public static final RegistryObject<Item> NETHERITE_KNIGHT_BOOTS = ITEMS.register("netherite_knight_boots", () -> new KnightArmorBootsItem(ModArmorMaterials.NETHERITE_KNIGHT_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab), "netherite"));

    public static final RegistryObject<Item> SILVER_HELMET = ITEMS.register("silver_helmet", () -> new ArmorItem(ModArmorMaterials.SILVER_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_CHESTPLATE = ITEMS.register("silver_chestplate", () -> new ArmorItem(ModArmorMaterials.SILVER_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_LEGGINGS = ITEMS.register("silver_leggings", () -> new ArmorItem(ModArmorMaterials.SILVER_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_BOOTS = ITEMS.register("silver_boots", () -> new ArmorItem(ModArmorMaterials.SILVER_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> STEEL_HELMET = ITEMS.register("steel_helmet", () -> new ArmorItem(ModArmorMaterials.STEEL_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_CHESTPLATE = ITEMS.register("steel_chestplate", () -> new ArmorItem(ModArmorMaterials.STEEL_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_LEGGINGS = ITEMS.register("steel_leggings", () -> new ArmorItem(ModArmorMaterials.STEEL_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_BOOTS = ITEMS.register("steel_boots", () -> new ArmorItem(ModArmorMaterials.STEEL_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> GAMBESON_CHESTPLATE = ITEMS.register("gambeson_chestplate", () -> new ArmorItem(ModArmorMaterials.GAMBESON_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> NETHERITE_HORSE_ARMOR = ITEMS.register("netherite_horse_armor", () -> new HorseArmorItem(20, new ResourceLocation("medievalworlds", "textures/entity/horse/armor/horse_armor_netherite.png"), (new Item.Properties()).stacksTo(1).tab(MedievalWorlds.tab)));

    //public static final RegistryObject<Item> CROWN = ITEMS.register("crown", () -> new CrownItem(ArmorMaterial.GOLD, new Item.Properties().tab(MedievalWorlds.itemGroup)));

}
