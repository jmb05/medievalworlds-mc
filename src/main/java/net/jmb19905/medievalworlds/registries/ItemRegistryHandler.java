package net.jmb19905.medievalworlds.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.item.*;
import net.jmb19905.medievalworlds.item.armor.*;
import net.jmb19905.medievalworlds.tiers.*;
import net.jmb19905.medievalworlds.util.ConfigHandler;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class ItemRegistryHandler {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MedievalWorlds.MOD_ID);

    //Materials
    public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot", () -> new Item(new Item.Properties().group(MedievalWorlds.itemGroup).maxStackSize(64)));
    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Item.Properties().group(MedievalWorlds.itemGroup).maxStackSize(64)));

    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot", () -> new Item(new Item.Properties().group(MedievalWorlds.itemGroup).maxStackSize(64)));
    public static final RegistryObject<Item> TIN_NUGGET = ITEMS.register("tin_nugget", () -> new Item(new Item.Properties().group(MedievalWorlds.itemGroup).maxStackSize(64)));

    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot", () -> new Item(new Item.Properties().group(MedievalWorlds.itemGroup).maxStackSize(64)));
    public static final RegistryObject<Item> BRONZE_NUGGET = ITEMS.register("bronze_nugget", () -> new Item(new Item.Properties().group(MedievalWorlds.itemGroup).maxStackSize(64)));

    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().group(MedievalWorlds.itemGroup).maxStackSize(64)));
    public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget", () -> new Item(new Item.Properties().group(MedievalWorlds.itemGroup).maxStackSize(64)));

    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties().group(MedievalWorlds.itemGroup).maxStackSize(64)));
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", () -> new Item(new Item.Properties().group(MedievalWorlds.itemGroup).maxStackSize(64)));

    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby", () -> new Item(new Item.Properties().group(MedievalWorlds.itemGroup).maxStackSize(64)));

    public static final RegistryObject<Item> LONG_STICK = ITEMS.register("long_stick", () -> new Item(new Item.Properties().group(ItemGroup.MISC).maxStackSize(64)));

    public static final RegistryObject<Item> LONG_STRING = ITEMS.register("long_string", () -> new Item(new Item.Properties().group(ItemGroup.MISC).maxStackSize(64)));

    //Tools
    public static final RegistryObject<Item> TIN_PICKAXE = ITEMS.register("tin_pickaxe", () -> new PickaxeItem(TinItemTier.TIN_ITEM_TIER, -1, -2.8f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> TIN_AXE = ITEMS.register("tin_axe", () -> new AxeItem(TinItemTier.TIN_ITEM_TIER, 4, -3f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> TIN_SHOVEL = ITEMS.register("tin_shovel", () -> new ShovelItem(TinItemTier.TIN_ITEM_TIER, 0, -3, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> TIN_HOE = ITEMS.register("tin_hoe", () -> new HoeItem(TinItemTier.TIN_ITEM_TIER, 0, -3, new Item.Properties().group(MedievalWorlds.itemGroup)));

    public static final RegistryObject<Item> COPPER_PICKAXE = ITEMS.register("copper_pickaxe", () -> new PickaxeItem(CopperItemTier.COPPER_ITEM_TIER, -1, -2.8f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> COPPER_AXE = ITEMS.register("copper_axe", () -> new AxeItem(CopperItemTier.COPPER_ITEM_TIER, 4, -3f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel", () -> new ShovelItem(CopperItemTier.COPPER_ITEM_TIER, 0, -3, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("copper_hoe", () -> new HoeItem(CopperItemTier.COPPER_ITEM_TIER, 0, -3f, new Item.Properties().group(MedievalWorlds.itemGroup)));

    public static final RegistryObject<Item> BRONZE_PICKAXE = ITEMS.register("bronze_pickaxe", () -> new PickaxeItem(BronzeItemTier.BRONZE_ITEM_TIER, -1, -2.8f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> BRONZE_AXE = ITEMS.register("bronze_axe", () -> new AxeItem(BronzeItemTier.BRONZE_ITEM_TIER, 5, -3f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> BRONZE_SHOVEL = ITEMS.register("bronze_shovel", () -> new ShovelItem(BronzeItemTier.BRONZE_ITEM_TIER, 0, -3, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> BRONZE_HOE = ITEMS.register("bronze_hoe", () -> new HoeItem(BronzeItemTier.BRONZE_ITEM_TIER, 0, -3f, new Item.Properties().group(MedievalWorlds.itemGroup)));

    public static final RegistryObject<Item> STEEL_PICKAXE = ITEMS.register("steel_pickaxe", () -> new PickaxeItem(SteelItemTier.STEEL_ITEM_TIER, -1, -2.8f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> STEEL_AXE = ITEMS.register("steel_axe", () -> new AxeItem(SteelItemTier.STEEL_ITEM_TIER, 5, -3.1f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> STEEL_SHOVEL = ITEMS.register("steel_shovel", () -> new ShovelItem(SteelItemTier.STEEL_ITEM_TIER, 0, -3, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> STEEL_HOE = ITEMS.register("steel_hoe", () -> new HoeItem(SteelItemTier.STEEL_ITEM_TIER, 0, -3f, new Item.Properties().group(MedievalWorlds.itemGroup)));

    public static final RegistryObject<Item> SILVER_PICKAXE = ITEMS.register("silver_pickaxe", () -> new PickaxeItem(SilverItemTier.SILVER_ITEM_TIER, -1, -2.8f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> SILVER_AXE = ITEMS.register("silver_axe", () -> new AxeItem(SilverItemTier.SILVER_ITEM_TIER, 5, -3.1f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> SILVER_SHOVEL = ITEMS.register("silver_shovel", () -> new ShovelItem(SilverItemTier.SILVER_ITEM_TIER, 0, -3, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> SILVER_HOE = ITEMS.register("silver_hoe", () -> new HoeItem(SilverItemTier.SILVER_ITEM_TIER, 0, -3f, new Item.Properties().group(MedievalWorlds.itemGroup)));

    //Weapons
    public static final RegistryObject<Item> TIN_SWORD = ITEMS.register("tin_sword", () -> new SwordItem(TinItemTier.TIN_ITEM_TIER, 1, -2.5f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword", () -> new SwordItem(TinItemTier.TIN_ITEM_TIER, 1, -2.2f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> BRONZE_SWORD = ITEMS.register("bronze_sword", () -> new SwordItem(TinItemTier.TIN_ITEM_TIER, 2, -2.8f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> STEEL_SWORD = ITEMS.register("steel_sword", () -> new SwordItem(TinItemTier.TIN_ITEM_TIER, 3, -2.8f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> SILVER_SWORD = ITEMS.register("silver_sword", () -> new SilverSword(TinItemTier.TIN_ITEM_TIER, 2, -2.4f, new Item.Properties().group(MedievalWorlds.itemGroup)));

    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer", () -> new HammerItem(IronBlockItemTier.IRON_BLOCK_ITEM_TIER, 10, -3.4f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> SILVER_HAMMER = ITEMS.register("silver_hammer", () -> new HammerItem(SilverBlockItemTier.SILVER_BLOCK_ITEM_TIER, 10, -3.3f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> STEEL_HAMMER = ITEMS.register("steel_hammer", () -> new HammerItem(SteelBlockItemTier.STEEL_BLOCK_ITEM_TIER, 10, -3.3f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> DIAMOND_HAMMER = ITEMS.register("diamond_hammer", () -> new HammerItem(DiamondBlockItemTier.DIAMOND_BLOCK_ITEM_TIER, 10, -3.2f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> NETHERITE_HAMMER = ITEMS.register("netherite_hammer", () -> new HammerItem(NetheriteBlockItemTier.NETHERITE_BLOCK_ITEM_TIER, 10, -3.3f, new Item.Properties().group(MedievalWorlds.itemGroup)));

    public static final RegistryObject<Item> IRON_BATTLE_AXE = ITEMS.register("iron_battle_axe", () -> new AxeItem(IronBlockItemTier.IRON_BLOCK_ITEM_TIER, 8, -3, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> SILVER_BATTLE_AXE = ITEMS.register("silver_battle_axe", () -> new AxeItem(SilverBlockItemTier.SILVER_BLOCK_ITEM_TIER, 8, -3, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> STEEL_BATTLE_AXE = ITEMS.register("steel_battle_axe", () -> new AxeItem(SteelBlockItemTier.STEEL_BLOCK_ITEM_TIER, 8, -3, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> DIAMOND_BATTLE_AXE = ITEMS.register("diamond_battle_axe", () -> new AxeItem(DiamondBlockItemTier.DIAMOND_BLOCK_ITEM_TIER, 8, -2.9f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> NETHERITE_BATTLE_AXE = ITEMS.register("netherite_battle_axe", () -> new AxeItem(NetheriteBlockItemTier.NETHERITE_BLOCK_ITEM_TIER, 8, -3f, new Item.Properties().group(MedievalWorlds.itemGroup)));

    public static final RegistryObject<Item> IRON_LONGSWORD = ITEMS.register("iron_longsword", () -> new SwordItem(IronBlockItemTier.IRON_BLOCK_ITEM_TIER, (int) (ConfigHandler.COMMON.ironLongswordAttackDamage.get() - IronBlockItemTier.IRON_BLOCK_ITEM_TIER.getAttackDamage()) - 1, -2.8f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> SILVER_LONGSWORD = ITEMS.register("silver_longsword", () -> new SwordItem(SteelBlockItemTier.STEEL_BLOCK_ITEM_TIER, (int) (ConfigHandler.COMMON.silverLongswordAttackDamage.get() - SilverBlockItemTier.SILVER_BLOCK_ITEM_TIER.getAttackDamage()) - 2, -2.8f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> STEEL_LONGSWORD = ITEMS.register("steel_longsword", () -> new SwordItem(SteelBlockItemTier.STEEL_BLOCK_ITEM_TIER, (int) (ConfigHandler.COMMON.steelLongswordAttackDamage.get() - IronBlockItemTier.IRON_BLOCK_ITEM_TIER.getAttackDamage()) - 1, -2.8f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> DIAMOND_LONGSWORD = ITEMS.register("diamond_longsword", () -> new SwordItem(DiamondBlockItemTier.DIAMOND_BLOCK_ITEM_TIER, (int) (ConfigHandler.COMMON.diamondLongswordAttackDamage.get() - DiamondBlockItemTier.DIAMOND_BLOCK_ITEM_TIER.getAttackDamage()) - 1, -2.7f, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> NETHERITE_LONGSWORD = ITEMS.register("netherite_longsword", () -> new SwordItem(NetheriteBlockItemTier.NETHERITE_BLOCK_ITEM_TIER, (int) (ConfigHandler.COMMON.netheriteLongswordAttackDamage.get() - NetheriteBlockItemTier.NETHERITE_BLOCK_ITEM_TIER.getAttackDamage()) - 1, -2.7f, new Item.Properties().group(MedievalWorlds.itemGroup)));

    public static final RegistryObject<Item> IRON_DAGGER = ITEMS.register("iron_dagger", () -> new SwordItem(ItemTier.IRON, 0, 0, new Item.Properties().maxDamage(800).group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> SILVER_DAGGER = ITEMS.register("silver_dagger", () -> new SwordItem(SilverItemTier.SILVER_ITEM_TIER, -1, 0, new Item.Properties().maxDamage(600).group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> STEEL_DAGGER = ITEMS.register("steel_dagger", () -> new SwordItem(SteelItemTier.STEEL_ITEM_TIER, 0, 0, new Item.Properties().maxDamage(1000).group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> DIAMOND_DAGGER = ITEMS.register("diamond_dagger", () -> new SwordItem(ItemTier.DIAMOND, 0, 0, new Item.Properties().maxDamage(1500).group(MedievalWorlds.itemGroup)));

    public static final RegistryObject<Item> LONGBOW = ITEMS.register("longbow", () -> new LongbowItem(new Item.Properties().group(MedievalWorlds.itemGroup).maxDamage(1200)));

    public static final RegistryObject<Item> IRON_LANCE = ITEMS.register("iron_lance", () -> new LanceItem(10, new Item.Properties().maxDamage(800).group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> SILVER_LANCE = ITEMS.register("silver_lance", () -> new LanceItem(11, new Item.Properties().maxDamage(600).group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> STEEL_LANCE = ITEMS.register("steel_lance", () -> new LanceItem(12, new Item.Properties().maxDamage(1000).group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> DIAMOND_LANCE = ITEMS.register("diamond_lance", () -> new LanceItem(14, new Item.Properties().maxDamage(1500).group(MedievalWorlds.itemGroup)));

    //TODO: add spears

    //Armor
    public static final RegistryObject<Item> COPPER_HELMET = ITEMS.register("copper_helmet", () -> new ArmorItem(ModArmorMaterials.COPPER_MATERIAL, EquipmentSlotType.HEAD, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate", () -> new ArmorItem(ModArmorMaterials.COPPER_MATERIAL, EquipmentSlotType.CHEST, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> COPPER_LEGGINGS = ITEMS.register("copper_leggings", () -> new ArmorItem(ModArmorMaterials.COPPER_MATERIAL, EquipmentSlotType.LEGS, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> COPPER_BOOTS = ITEMS.register("copper_boots", () -> new ArmorItem(ModArmorMaterials.COPPER_MATERIAL, EquipmentSlotType.FEET, new Item.Properties().group(MedievalWorlds.itemGroup)));

    public static final RegistryObject<Item> TIN_HELMET = ITEMS.register("tin_helmet", () -> new ArmorItem(ModArmorMaterials.TIN_MATERIAL, EquipmentSlotType.HEAD, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> TIN_CHESTPLATE = ITEMS.register("tin_chestplate", () -> new ArmorItem(ModArmorMaterials.TIN_MATERIAL, EquipmentSlotType.CHEST, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> TIN_LEGGINGS = ITEMS.register("tin_leggings", () -> new ArmorItem(ModArmorMaterials.TIN_MATERIAL, EquipmentSlotType.LEGS, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> TIN_BOOTS = ITEMS.register("tin_boots", () -> new ArmorItem(ModArmorMaterials.TIN_MATERIAL, EquipmentSlotType.FEET, new Item.Properties().group(MedievalWorlds.itemGroup)));

    public static final RegistryObject<Item> BRONZE_HELMET = ITEMS.register("bronze_helmet", () -> new ArmorItem(ModArmorMaterials.BRONZE_MATERIAL, EquipmentSlotType.HEAD, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> BRONZE_CHESTPLATE = ITEMS.register("bronze_chestplate", () -> new ArmorItem(ModArmorMaterials.BRONZE_MATERIAL, EquipmentSlotType.CHEST, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> BRONZE_LEGGINGS = ITEMS.register("bronze_leggings", () -> new ArmorItem(ModArmorMaterials.BRONZE_MATERIAL, EquipmentSlotType.LEGS, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> BRONZE_BOOTS = ITEMS.register("bronze_boots", () -> new ArmorItem(ModArmorMaterials.BRONZE_MATERIAL, EquipmentSlotType.FEET, new Item.Properties().group(MedievalWorlds.itemGroup)));

    public static final RegistryObject<Item> WARRIOR_HELMET = ITEMS.register("warrior_helmet", () -> new WarriorArmorItem(ModArmorMaterials.WARRIOR_MATERIAL, EquipmentSlotType.HEAD, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> WARRIOR_CHESTPLATE = ITEMS.register("warrior_chestplate", () -> new WarriorArmorItem(ModArmorMaterials.WARRIOR_MATERIAL, EquipmentSlotType.CHEST, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> WARRIOR_LEGGINGS = ITEMS.register("warrior_leggings", () -> new WarriorArmorItem(ModArmorMaterials.WARRIOR_MATERIAL, EquipmentSlotType.LEGS, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> WARRIOR_BOOTS = ITEMS.register("warrior_boots", () -> new WarriorArmorItem(ModArmorMaterials.WARRIOR_MATERIAL, EquipmentSlotType.FEET, new Item.Properties().group(MedievalWorlds.itemGroup)));

    public static final RegistryObject<Item> IRON_KNIGHT_HELMET = ITEMS.register("iron_knight_helmet", () -> new KnightArmorHelmetItem(ModArmorMaterials.IRON_KNIGHT_MATERIAL, EquipmentSlotType.HEAD, new Item.Properties().group(MedievalWorlds.itemGroup), "iron"));
    public static final RegistryObject<Item> IRON_KNIGHT_CHESTPLATE = ITEMS.register("iron_knight_chestplate", () -> new KnightArmorChestplateItem(ModArmorMaterials.IRON_KNIGHT_MATERIAL, EquipmentSlotType.CHEST, new Item.Properties().group(MedievalWorlds.itemGroup), "iron"));
    public static final RegistryObject<Item> IRON_KNIGHT_LEGGINGS = ITEMS.register("iron_knight_leggings", () -> new KnightArmorLeggingsItem(ModArmorMaterials.IRON_KNIGHT_MATERIAL, EquipmentSlotType.LEGS, new Item.Properties().group(MedievalWorlds.itemGroup), "iron"));
    public static final RegistryObject<Item> IRON_KNIGHT_BOOTS = ITEMS.register("iron_knight_boots", () -> new KnightArmorBootsItem(ModArmorMaterials.IRON_KNIGHT_MATERIAL, EquipmentSlotType.FEET, new Item.Properties().group(MedievalWorlds.itemGroup), "iron"));

    public static final RegistryObject<Item> SILVER_KNIGHT_HELMET = ITEMS.register("silver_knight_helmet", () -> new KnightArmorHelmetItem(ModArmorMaterials.SILVER_KNIGHT_MATERIAL, EquipmentSlotType.HEAD, new Item.Properties().group(MedievalWorlds.itemGroup), "silver"));
    public static final RegistryObject<Item> SILVER_KNIGHT_CHESTPLATE = ITEMS.register("silver_knight_chestplate", () -> new KnightArmorChestplateItem(ModArmorMaterials.SILVER_KNIGHT_MATERIAL, EquipmentSlotType.CHEST, new Item.Properties().group(MedievalWorlds.itemGroup), "silver"));
    public static final RegistryObject<Item> SILVER_KNIGHT_LEGGINGS = ITEMS.register("silver_knight_leggings", () -> new KnightArmorLeggingsItem(ModArmorMaterials.SILVER_KNIGHT_MATERIAL, EquipmentSlotType.LEGS, new Item.Properties().group(MedievalWorlds.itemGroup), "silver"));
    public static final RegistryObject<Item> SILVER_KNIGHT_BOOTS = ITEMS.register("silver_knight_boots", () -> new KnightArmorBootsItem(ModArmorMaterials.SILVER_KNIGHT_MATERIAL, EquipmentSlotType.FEET, new Item.Properties().group(MedievalWorlds.itemGroup), "silver"));

    public static final RegistryObject<Item> STEEL_KNIGHT_HELMET = ITEMS.register("steel_knight_helmet", () -> new KnightArmorHelmetItem(ModArmorMaterials.STEEL_KNIGHT_MATERIAL, EquipmentSlotType.HEAD, new Item.Properties().group(MedievalWorlds.itemGroup), "steel"));
    public static final RegistryObject<Item> STEEL_KNIGHT_CHESTPLATE = ITEMS.register("steel_knight_chestplate", () -> new KnightArmorChestplateItem(ModArmorMaterials.STEEL_KNIGHT_MATERIAL, EquipmentSlotType.CHEST, new Item.Properties().group(MedievalWorlds.itemGroup), "steel"));
    public static final RegistryObject<Item> STEEL_KNIGHT_LEGGINGS = ITEMS.register("steel_knight_leggings", () -> new KnightArmorLeggingsItem(ModArmorMaterials.STEEL_KNIGHT_MATERIAL, EquipmentSlotType.LEGS, new Item.Properties().group(MedievalWorlds.itemGroup), "steel"));
    public static final RegistryObject<Item> STEEL_KNIGHT_BOOTS = ITEMS.register("steel_knight_boots", () -> new KnightArmorBootsItem(ModArmorMaterials.STEEL_KNIGHT_MATERIAL, EquipmentSlotType.FEET, new Item.Properties().group(MedievalWorlds.itemGroup), "steel"));

    public static final RegistryObject<Item> DIAMOND_KNIGHT_HELMET = ITEMS.register("diamond_knight_helmet", () -> new KnightArmorHelmetItem(ModArmorMaterials.DIAMOND_KNIGHT_MATERIAL, EquipmentSlotType.HEAD, new Item.Properties().group(MedievalWorlds.itemGroup), "diamond"));
    public static final RegistryObject<Item> DIAMOND_KNIGHT_CHESTPLATE = ITEMS.register("diamond_knight_chestplate", () -> new KnightArmorChestplateItem(ModArmorMaterials.DIAMOND_KNIGHT_MATERIAL, EquipmentSlotType.CHEST, new Item.Properties().group(MedievalWorlds.itemGroup), "diamond"));
    public static final RegistryObject<Item> DIAMOND_KNIGHT_LEGGINGS = ITEMS.register("diamond_knight_leggings", () -> new KnightArmorLeggingsItem(ModArmorMaterials.DIAMOND_KNIGHT_MATERIAL, EquipmentSlotType.LEGS, new Item.Properties().group(MedievalWorlds.itemGroup), "diamond"));
    public static final RegistryObject<Item> DIAMOND_KNIGHT_BOOTS = ITEMS.register("diamond_knight_boots", () -> new KnightArmorBootsItem(ModArmorMaterials.DIAMOND_KNIGHT_MATERIAL, EquipmentSlotType.FEET, new Item.Properties().group(MedievalWorlds.itemGroup), "diamond"));

    public static final RegistryObject<Item> NETHERITE_KNIGHT_HELMET = ITEMS.register("netherite_knight_helmet", () -> new KnightArmorHelmetItem(ModArmorMaterials.NETHERITE_KNIGHT_MATERIAL, EquipmentSlotType.HEAD, new Item.Properties().group(MedievalWorlds.itemGroup), "netherite"));
    public static final RegistryObject<Item> NETHERITE_KNIGHT_CHESTPLATE = ITEMS.register("netherite_knight_chestplate", () -> new KnightArmorChestplateItem(ModArmorMaterials.NETHERITE_KNIGHT_MATERIAL, EquipmentSlotType.CHEST, new Item.Properties().group(MedievalWorlds.itemGroup), "netherite"));
    public static final RegistryObject<Item> NETHERITE_KNIGHT_LEGGINGS = ITEMS.register("netherite_knight_leggings", () -> new KnightArmorLeggingsItem(ModArmorMaterials.NETHERITE_KNIGHT_MATERIAL, EquipmentSlotType.LEGS, new Item.Properties().group(MedievalWorlds.itemGroup), "netherite"));
    public static final RegistryObject<Item> NETHERITE_KNIGHT_BOOTS = ITEMS.register("netherite_knight_boots", () -> new KnightArmorBootsItem(ModArmorMaterials.NETHERITE_KNIGHT_MATERIAL, EquipmentSlotType.FEET, new Item.Properties().group(MedievalWorlds.itemGroup), "netherite"));

    public static final RegistryObject<Item> SILVER_HELMET = ITEMS.register("silver_helmet", () -> new ArmorItem(ModArmorMaterials.SILVER_MATERIAL, EquipmentSlotType.HEAD, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> SILVER_CHESTPLATE = ITEMS.register("silver_chestplate", () -> new ArmorItem(ModArmorMaterials.SILVER_MATERIAL, EquipmentSlotType.CHEST, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> SILVER_LEGGINGS = ITEMS.register("silver_leggings", () -> new ArmorItem(ModArmorMaterials.SILVER_MATERIAL, EquipmentSlotType.LEGS, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> SILVER_BOOTS = ITEMS.register("silver_boots", () -> new ArmorItem(ModArmorMaterials.SILVER_MATERIAL, EquipmentSlotType.FEET, new Item.Properties().group(MedievalWorlds.itemGroup)));

    public static final RegistryObject<Item> STEEL_HELMET = ITEMS.register("steel_helmet", () -> new ArmorItem(ModArmorMaterials.STEEL_MATERIAL, EquipmentSlotType.HEAD, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> STEEL_CHESTPLATE = ITEMS.register("steel_chestplate", () -> new ArmorItem(ModArmorMaterials.STEEL_MATERIAL, EquipmentSlotType.CHEST, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> STEEL_LEGGINGS = ITEMS.register("steel_leggings", () -> new ArmorItem(ModArmorMaterials.STEEL_MATERIAL, EquipmentSlotType.LEGS, new Item.Properties().group(MedievalWorlds.itemGroup)));
    public static final RegistryObject<Item> STEEL_BOOTS = ITEMS.register("steel_boots", () -> new ArmorItem(ModArmorMaterials.STEEL_MATERIAL, EquipmentSlotType.FEET, new Item.Properties().group(MedievalWorlds.itemGroup)));

    public static final RegistryObject<Item> GAMBESON_CHESTPLATE = ITEMS.register("gambeson_chestplate", () -> new ArmorItem(ModArmorMaterials.GAMBESON_MATERIAL, EquipmentSlotType.CHEST, new Item.Properties().group(MedievalWorlds.itemGroup)));

    //public static final RegistryObject<Item> CROWN = ITEMS.register("crown", () -> new CrownItem(ArmorMaterial.GOLD, new Item.Properties().group(MedievalWorlds.itemGroup)));

}
