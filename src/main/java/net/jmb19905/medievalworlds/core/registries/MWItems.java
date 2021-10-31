package net.jmb19905.medievalworlds.core.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.item.*;
import net.jmb19905.medievalworlds.common.item.armor.*;
import net.jmb19905.medievalworlds.common.item.heated.HeatedBarItem;
import net.jmb19905.medievalworlds.common.item.heated.HeatedBloomItem;
import net.jmb19905.medievalworlds.common.item.heated.HeatedIngotItem;
import net.jmb19905.medievalworlds.common.item.heated.HeatedPlateItem;
import net.jmb19905.medievalworlds.common.item.lance.LanceItem;
import net.jmb19905.medievalworlds.common.item.tiers.*;
import net.jmb19905.medievalworlds.util.ConfigHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class MWItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MedievalWorlds.MOD_ID);

    //Materials
    public static final RegistryObject<Item> RAW_TIM = ITEMS.register("raw_tin", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> TIN_NUGGET = ITEMS.register("tin_nugget", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> BRONZE_NUGGET = ITEMS.register("bronze_nugget", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<Item> RAW_SILVER = ITEMS.register("raw_silver", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<Item> LONG_STICK = ITEMS.register("long_stick", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> LONG_STRING = ITEMS.register("long_string", () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<HeatedIngotItem> HEATED_IRON_INGOT_ITEM = ITEMS.register("heated_iron_ingot", () -> new HeatedIngotItem(Items.IRON_INGOT, "iron", new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedIngotItem> HEATED_STEEL_INGOT_ITEM = ITEMS.register("heated_steel_ingot", () -> new HeatedIngotItem(MWItems.STEEL_INGOT.get(), "steel", new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedIngotItem> HEATED_COPPER_INGOT_ITEM = ITEMS.register("heated_copper_ingot", () -> new HeatedIngotItem(Items.COPPER_INGOT, "copper", new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedIngotItem> HEATED_TIN_INGOT_ITEM = ITEMS.register("heated_tin_ingot", () -> new HeatedIngotItem(MWItems.TIN_INGOT.get(), "tin", new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedIngotItem> HEATED_BRONZE_INGOT_ITEM = ITEMS.register("heated_bronze_ingot", () -> new HeatedIngotItem(MWItems.BRONZE_INGOT.get(), "bronze", new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedIngotItem> HEATED_SILVER_INGOT_ITEM = ITEMS.register("heated_silver_ingot", () -> new HeatedIngotItem(MWItems.SILVER_INGOT.get(), "silver", new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedIngotItem> HEATED_GOLD_INGOT_ITEM = ITEMS.register("heated_gold_ingot", () -> new HeatedIngotItem(Items.GOLD_INGOT, "gold", new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedIngotItem> HEATED_NETHERITE_INGOT_ITEM = ITEMS.register("heated_netherite_ingot", () -> new HeatedIngotItem(Items.NETHERITE_INGOT, "netherite", new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64).fireResistant()));

    public static final RegistryObject<PlateItem> IRON_PLATE = ITEMS.register("iron_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> STEEL_PLATE = ITEMS.register("steel_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> COPPER_PLATE = ITEMS.register("copper_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> TIN_PLATE = ITEMS.register("tin_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> BRONZE_PLATE = ITEMS.register("bronze_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> SILVER_PLATE = ITEMS.register("silver_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> GOLD_PLATE = ITEMS.register("gold_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> NETHERITE_PLATE = ITEMS.register("netherite_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<HeatedPlateItem> HEATED_IRON_PLATE_ITEM = ITEMS.register("heated_iron_plate", () -> new HeatedPlateItem(MWItems.IRON_PLATE.get(), "iron", 0, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_STEEL_PLATE_ITEM = ITEMS.register("heated_steel_plate", () -> new HeatedPlateItem(MWItems.STEEL_PLATE.get(), "steel", 0, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_COPPER_PLATE_ITEM = ITEMS.register("heated_copper_plate", () -> new HeatedPlateItem(MWItems.COPPER_PLATE.get(), "copper", 0, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_TIN_PLATE_ITEM = ITEMS.register("heated_tin_plate", () -> new HeatedPlateItem(MWItems.TIN_PLATE.get(), "tin", 0, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_BRONZE_PLATE_ITEM = ITEMS.register("heated_bronze_plate", () -> new HeatedPlateItem(MWItems.BRONZE_PLATE.get(), "bronze", 0, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_SILVER_PLATE_ITEM = ITEMS.register("heated_silver_plate", () -> new HeatedPlateItem(MWItems.SILVER_PLATE.get(), "silver", 0, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_GOLD_PLATE_ITEM = ITEMS.register("heated_gold_plate", () -> new HeatedPlateItem(MWItems.GOLD_PLATE.get(), "gold", 0, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_NETHERITE_PLATE_ITEM = ITEMS.register("heated_netherite_plate", () -> new HeatedPlateItem(MWItems.NETHERITE_PLATE.get(), "netherite", 0, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64).fireResistant()));

    public static final RegistryObject<PlateItem> MEDIUM_IRON_PLATE = ITEMS.register("medium_iron_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> MEDIUM_STEEL_PLATE = ITEMS.register("medium_steel_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> MEDIUM_COPPER_PLATE = ITEMS.register("medium_copper_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> MEDIUM_TIN_PLATE = ITEMS.register("medium_tin_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> MEDIUM_BRONZE_PLATE = ITEMS.register("medium_bronze_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> MEDIUM_SILVER_PLATE = ITEMS.register("medium_silver_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> MEDIUM_GOLD_PLATE = ITEMS.register("medium_gold_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> MEDIUM_NETHERITE_PLATE = ITEMS.register("medium_netherite_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<HeatedPlateItem> HEATED_MEDIUM_IRON_PLATE_ITEM = ITEMS.register("heated_medium_iron_plate", () -> new HeatedPlateItem(MWItems.IRON_PLATE.get(), "iron", 1, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_MEDIUM_STEEL_PLATE_ITEM = ITEMS.register("heated_medium_steel_plate", () -> new HeatedPlateItem(MWItems.STEEL_PLATE.get(), "steel", 1, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_MEDIUM_COPPER_PLATE_ITEM = ITEMS.register("heated_medium_copper_plate", () -> new HeatedPlateItem(MWItems.COPPER_PLATE.get(), "copper", 1, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_MEDIUM_TIN_PLATE_ITEM = ITEMS.register("heated_medium_tin_plate", () -> new HeatedPlateItem(MWItems.TIN_PLATE.get(), "tin", 1, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_MEDIUM_BRONZE_PLATE_ITEM = ITEMS.register("heated_medium_bronze_plate", () -> new HeatedPlateItem(MWItems.BRONZE_PLATE.get(), "bronze", 1, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_MEDIUM_SILVER_PLATE_ITEM = ITEMS.register("heated_medium_silver_plate", () -> new HeatedPlateItem(MWItems.SILVER_PLATE.get(), "silver", 1, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_MEDIUM_GOLD_PLATE_ITEM = ITEMS.register("heated_medium_gold_plate", () -> new HeatedPlateItem(MWItems.GOLD_PLATE.get(), "gold", 1, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_MEDIUM_NETHERITE_PLATE_ITEM = ITEMS.register("heated_medium_netherite_plate", () -> new HeatedPlateItem(MWItems.NETHERITE_PLATE.get(), "netherite", 1, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64).fireResistant()));

    public static final RegistryObject<PlateItem> LARGE_IRON_PLATE = ITEMS.register("large_iron_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> LARGE_STEEL_PLATE = ITEMS.register("large_steel_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> LARGE_COPPER_PLATE = ITEMS.register("large_copper_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> LARGE_TIN_PLATE = ITEMS.register("large_tin_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> LARGE_BRONZE_PLATE = ITEMS.register("large_bronze_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> LARGE_SILVER_PLATE = ITEMS.register("large_silver_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> LARGE_GOLD_PLATE = ITEMS.register("large_gold_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<PlateItem> LARGE_NETHERITE_PLATE = ITEMS.register("large_netherite_plate", () -> new PlateItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<HeatedPlateItem> HEATED_LARGE_IRON_PLATE_ITEM = ITEMS.register("heated_large_iron_plate", () -> new HeatedPlateItem(MWItems.IRON_PLATE.get(), "iron", 2, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_LARGE_STEEL_PLATE_ITEM = ITEMS.register("heated_large_steel_plate", () -> new HeatedPlateItem(MWItems.STEEL_PLATE.get(), "steel", 2, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_LARGE_COPPER_PLATE_ITEM = ITEMS.register("heated_large_copper_plate", () -> new HeatedPlateItem(MWItems.COPPER_PLATE.get(), "copper", 2, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_LARGE_TIN_PLATE_ITEM = ITEMS.register("heated_large_tin_plate", () -> new HeatedPlateItem(MWItems.TIN_PLATE.get(), "tin", 2, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_LARGE_BRONZE_PLATE_ITEM = ITEMS.register("heated_large_bronze_plate", () -> new HeatedPlateItem(MWItems.BRONZE_PLATE.get(), "bronze", 2, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_LARGE_SILVER_PLATE_ITEM = ITEMS.register("heated_large_silver_plate", () -> new HeatedPlateItem(MWItems.SILVER_PLATE.get(), "silver", 2, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_LARGE_GOLD_PLATE_ITEM = ITEMS.register("heated_large_gold_plate", () -> new HeatedPlateItem(MWItems.GOLD_PLATE.get(), "gold", 2, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedPlateItem> HEATED_LARGE_NETHERITE_PLATE_ITEM = ITEMS.register("heated_large_netherite_plate", () -> new HeatedPlateItem(MWItems.NETHERITE_PLATE.get(), "netherite", 2, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64).fireResistant()));

    public static final RegistryObject<BarItem> IRON_BAR = ITEMS.register("iron_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<BarItem> STEEL_BAR = ITEMS.register("steel_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<BarItem> TIN_BAR = ITEMS.register("tin_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<BarItem> COPPER_BAR = ITEMS.register("copper_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<BarItem> BRONZE_BAR = ITEMS.register("bronze_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<BarItem> SILVER_BAR = ITEMS.register("silver_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<BarItem> NETHERITE_BAR = ITEMS.register("netherite_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<HeatedBarItem> HEATED_IRON_BAR = ITEMS.register("heated_iron_bar", () -> new HeatedBarItem(IRON_BAR.get(), "iron", 0, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedBarItem> HEATED_STEEL_BAR = ITEMS.register("heated_steel_bar", () -> new HeatedBarItem(STEEL_BAR.get(), "steel", 0, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedBarItem> HEATED_TIN_BAR = ITEMS.register("heated_tin_bar", () -> new HeatedBarItem(TIN_BAR.get(), "tin", 0, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedBarItem> HEATED_COPPER_BAR = ITEMS.register("heated_copper_bar", () -> new HeatedBarItem(COPPER_BAR.get(), "copper", 0, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedBarItem> HEATED_BRONZE_BAR = ITEMS.register("heated_bronze_bar", () -> new HeatedBarItem(BRONZE_BAR.get(), "bronze", 0, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedBarItem> HEATED_SILVER_BAR = ITEMS.register("heated_silver_bar", () -> new HeatedBarItem(SILVER_BAR.get(), "silver", 0, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedBarItem> HEATED_NETHERITE_BAR = ITEMS.register("heated_netherite_bar", () -> new HeatedBarItem(NETHERITE_BAR.get(), "netherite", 0, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<BarItem> MEDIUM_IRON_BAR = ITEMS.register("medium_iron_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<BarItem> MEDIUM_STEEL_BAR = ITEMS.register("medium_steel_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<BarItem> MEDIUM_TIN_BAR = ITEMS.register("medium_tin_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<BarItem> MEDIUM_COPPER_BAR = ITEMS.register("medium_copper_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<BarItem> MEDIUM_BRONZE_BAR = ITEMS.register("medium_bronze_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<BarItem> MEDIUM_SILVER_BAR = ITEMS.register("medium_silver_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<BarItem> MEDIUM_NETHERITE_BAR = ITEMS.register("medium_netherite_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<HeatedBarItem> HEATED_MEDIUM_IRON_BAR = ITEMS.register("heated_medium_iron_bar", () -> new HeatedBarItem(MEDIUM_IRON_BAR.get(), "iron", 1, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedBarItem> HEATED_MEDIUM_STEEL_BAR = ITEMS.register("heated_medium_steel_bar", () -> new HeatedBarItem(MEDIUM_STEEL_BAR.get(), "steel", 1, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedBarItem> HEATED_MEDIUM_TIN_BAR = ITEMS.register("heated_medium_tin_bar", () -> new HeatedBarItem(MEDIUM_TIN_BAR.get(), "tin", 1, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedBarItem> HEATED_MEDIUM_COPPER_BAR = ITEMS.register("heated_medium_copper_bar", () -> new HeatedBarItem(MEDIUM_COPPER_BAR.get(), "copper", 1, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedBarItem> HEATED_MEDIUM_BRONZE_BAR = ITEMS.register("heated_medium_bronze_bar", () -> new HeatedBarItem(MEDIUM_BRONZE_BAR.get(), "bronze", 1, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedBarItem> HEATED_MEDIUM_SILVER_BAR = ITEMS.register("heated_medium_silver_bar", () -> new HeatedBarItem(MEDIUM_SILVER_BAR.get(), "silver", 1, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedBarItem> HEATED_MEDIUM_NETHERITE_BAR = ITEMS.register("heated_medium_netherite_bar", () -> new HeatedBarItem(MEDIUM_NETHERITE_BAR.get(), "netherite", 1, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<BarItem> LARGE_IRON_BAR = ITEMS.register("large_iron_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<BarItem> LARGE_STEEL_BAR = ITEMS.register("large_steel_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<BarItem> LARGE_TIN_BAR = ITEMS.register("large_tin_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<BarItem> LARGE_COPPER_BAR = ITEMS.register("large_copper_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<BarItem> LARGE_BRONZE_BAR = ITEMS.register("large_bronze_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<BarItem> LARGE_SILVER_BAR = ITEMS.register("large_silver_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<BarItem> LARGE_NETHERITE_BAR = ITEMS.register("large_netherite_bar", () -> new BarItem(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<HeatedBarItem> HEATED_LARGE_IRON_BAR = ITEMS.register("heated_large_iron_bar", () -> new HeatedBarItem(MEDIUM_IRON_BAR.get(), "iron", 2, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedBarItem> HEATED_LARGE_STEEL_BAR = ITEMS.register("heated_large_steel_bar", () -> new HeatedBarItem(MEDIUM_STEEL_BAR.get(), "steel", 2, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedBarItem> HEATED_LARGE_TIN_BAR = ITEMS.register("heated_large_tin_bar", () -> new HeatedBarItem(MEDIUM_TIN_BAR.get(), "tin", 2, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedBarItem> HEATED_LARGE_COPPER_BAR = ITEMS.register("heated_large_copper_bar", () -> new HeatedBarItem(MEDIUM_COPPER_BAR.get(), "copper", 2, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedBarItem> HEATED_LARGE_BRONZE_BAR = ITEMS.register("heated_large_bronze_bar", () -> new HeatedBarItem(MEDIUM_BRONZE_BAR.get(), "bronze", 2, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedBarItem> HEATED_LARGE_SILVER_BAR = ITEMS.register("heated_large_silver_bar", () -> new HeatedBarItem(MEDIUM_SILVER_BAR.get(), "silver", 2, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<HeatedBarItem> HEATED_LARGE_NETHERITE_BAR = ITEMS.register("heated_large_netherite_bar", () -> new HeatedBarItem(MEDIUM_NETHERITE_BAR.get(), "netherite", 2, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<Item> IRON_BLOOM = ITEMS.register("iron_bloom", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> STEEL_BLOOM = ITEMS.register("steel_bloom", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<Item> HEATED_IRON_BLOOM = ITEMS.register("heated_iron_bloom", () -> new HeatedBloomItem(IRON_BLOOM.get(), "iron", new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> HEATED_STEEL_BLOOM = ITEMS.register("heated_steel_bloom", () -> new HeatedBloomItem(STEEL_BLOOM.get(), "steel", new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

    public static final RegistryObject<Item> IRON_ARMOR_PLATE = ITEMS.register("iron_armor_plate", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> STEEL_ARMOR_PLATE = ITEMS.register("steel_armor_plate", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> COPPER_ARMOR_PLATE = ITEMS.register("copper_armor_plate", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> TIN_ARMOR_PLATE = ITEMS.register("tin_armor_plate", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> BRONZE_ARMOR_PLATE = ITEMS.register("bronze_armor_plate", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> SILVER_ARMOR_PLATE = ITEMS.register("silver_armor_plate", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> GOLD_ARMOR_PLATE = ITEMS.register("gold_armor_plate", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));
    public static final RegistryObject<Item> NETHERITE_ARMOR_PLATE = ITEMS.register("netherite_armor_plate", () -> new Item(new Item.Properties().tab(MedievalWorlds.tab).stacksTo(64)));

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

    public static final RegistryObject<Item> WOODEN_FORGE_HAMMER = ITEMS.register("wooden_forge_hammer", () -> new ForgeHammerItem(Tiers.WOOD, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STONE_FORGE_HAMMER = ITEMS.register("stone_forge_hammer", () -> new ForgeHammerItem(Tiers.STONE, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> IRON_FORGE_HAMMER = ITEMS.register("iron_forge_hammer", () -> new ForgeHammerItem(Tiers.IRON, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_FORGE_HAMMER = ITEMS.register("steel_forge_hammer", () -> new ForgeHammerItem(MWItemTiers.STEEL_ITEM_TIER, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> NETHERITE_FORGE_HAMMER = ITEMS.register("netherite_forge_hammer", () -> new ForgeHammerItem(Tiers.NETHERITE, new Item.Properties().tab(MedievalWorlds.tab).fireResistant()));

    //Weapons
    public static final RegistryObject<Item> TIN_SWORD = ITEMS.register("tin_sword", () -> new SwordItem(MWItemTiers.TIN_ITEM_TIER, 1, -2.5f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword", () -> new SwordItem(MWItemTiers.TIN_ITEM_TIER, 1, -2.2f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> BRONZE_SWORD = ITEMS.register("bronze_sword", () -> new SwordItem(MWItemTiers.TIN_ITEM_TIER, 2, -2.8f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_SWORD = ITEMS.register("steel_sword", () -> new SwordItem(MWItemTiers.TIN_ITEM_TIER, 3, -2.8f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_SWORD = ITEMS.register("silver_sword", () -> new SilverSword(MWItemTiers.TIN_ITEM_TIER, 2, -2.4f, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer", () -> new HammerItem(MWItemTiers.IRON_BLOCK_ITEM_TIER, 10, -3.4f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_HAMMER = ITEMS.register("silver_hammer", () -> new HammerItem(MWItemTiers.SILVER_BLOCK_ITEM_TIER, 10, -3.3f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_HAMMER = ITEMS.register("steel_hammer", () -> new HammerItem(MWItemTiers.STEEL_BLOCK_ITEM_TIER, 10, -3.3f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> NETHERITE_HAMMER = ITEMS.register("netherite_hammer", () -> new HammerItem(MWItemTiers.NETHERITE_BLOCK_ITEM_TIER, 10, -3.3f, new Item.Properties().tab(MedievalWorlds.tab).fireResistant()));

    public static final RegistryObject<Item> IRON_BATTLE_AXE = ITEMS.register("iron_battle_axe", () -> new AxeItem(MWItemTiers.IRON_BLOCK_ITEM_TIER, 8, -3, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_BATTLE_AXE = ITEMS.register("silver_battle_axe", () -> new AxeItem(MWItemTiers.SILVER_BLOCK_ITEM_TIER, 8, -3, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_BATTLE_AXE = ITEMS.register("steel_battle_axe", () -> new AxeItem(MWItemTiers.STEEL_BLOCK_ITEM_TIER, 8, -3, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> NETHERITE_BATTLE_AXE = ITEMS.register("netherite_battle_axe", () -> new AxeItem(MWItemTiers.NETHERITE_BLOCK_ITEM_TIER, 8, -3f, new Item.Properties().tab(MedievalWorlds.tab).fireResistant()));

    public static final RegistryObject<Item> IRON_LONGSWORD = ITEMS.register("iron_longsword", () -> new SwordItem(MWItemTiers.IRON_BLOCK_ITEM_TIER, (int) (ConfigHandler.COMMON.ironLongswordAttackDamage.get() - MWItemTiers.IRON_BLOCK_ITEM_TIER.getAttackDamageBonus()) - 1, -2.8f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_LONGSWORD = ITEMS.register("silver_longsword", () -> new SwordItem(MWItemTiers.STEEL_BLOCK_ITEM_TIER, (int) (ConfigHandler.COMMON.silverLongswordAttackDamage.get() - MWItemTiers.SILVER_BLOCK_ITEM_TIER.getAttackDamageBonus()) - 2, -2.8f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_LONGSWORD = ITEMS.register("steel_longsword", () -> new SwordItem(MWItemTiers.STEEL_BLOCK_ITEM_TIER, (int) (ConfigHandler.COMMON.steelLongswordAttackDamage.get() - MWItemTiers.IRON_BLOCK_ITEM_TIER.getAttackDamageBonus()) - 1, -2.8f, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> NETHERITE_LONGSWORD = ITEMS.register("netherite_longsword", () -> new SwordItem(MWItemTiers.NETHERITE_BLOCK_ITEM_TIER, (int) (ConfigHandler.COMMON.netheriteLongswordAttackDamage.get() - MWItemTiers.NETHERITE_BLOCK_ITEM_TIER.getAttackDamageBonus()) - 1, -2.7f, new Item.Properties().tab(MedievalWorlds.tab).fireResistant()));

    public static final RegistryObject<Item> IRON_DAGGER = ITEMS.register("iron_dagger", () -> new SwordItem(Tiers.IRON, 0, 0, new Item.Properties().durability(800).tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_DAGGER = ITEMS.register("silver_dagger", () -> new SwordItem(MWItemTiers.SILVER_ITEM_TIER, -1, 0, new Item.Properties().durability(600).tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_DAGGER = ITEMS.register("steel_dagger", () -> new SwordItem(MWItemTiers.STEEL_ITEM_TIER, 0, 0, new Item.Properties().durability(1000).tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> LONGBOW = ITEMS.register("longbow", () -> new LongbowItem(new Item.Properties().tab(MedievalWorlds.tab).durability(1200)));

    public static final RegistryObject<Item> IRON_LANCE = ITEMS.register("iron_lance", () -> new LanceItem(2.5f, 20, new Item.Properties().durability(800).tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_LANCE = ITEMS.register("silver_lance", () -> new LanceItem(2.75f, 15, new Item.Properties().durability(600).tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_LANCE = ITEMS.register("steel_lance", () -> new LanceItem(3f, 25, new Item.Properties().durability(1000).tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> NETHERITE_LANCE = ITEMS.register("netherite_lance", () -> new LanceItem(3.5f, 30, new Item.Properties().durability(2000).tab(MedievalWorlds.tab).fireResistant()));

    public static final RegistryObject<SpearItem> IRON_SPEAR = ITEMS.register("iron_spear", () -> new SpearItem(6, 2, new Item.Properties().tab(MedievalWorlds.tab).stacksTo(1).durability(600)));

    //Armor
    public static final RegistryObject<Item> COPPER_HELMET = ITEMS.register("copper_helmet", () -> new ArmorItem(MWArmorMaterials.COPPER_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate", () -> new ArmorItem(MWArmorMaterials.COPPER_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> COPPER_LEGGINGS = ITEMS.register("copper_leggings", () -> new ArmorItem(MWArmorMaterials.COPPER_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> COPPER_BOOTS = ITEMS.register("copper_boots", () -> new ArmorItem(MWArmorMaterials.COPPER_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> TIN_HELMET = ITEMS.register("tin_helmet", () -> new ArmorItem(MWArmorMaterials.TIN_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> TIN_CHESTPLATE = ITEMS.register("tin_chestplate", () -> new ArmorItem(MWArmorMaterials.TIN_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> TIN_LEGGINGS = ITEMS.register("tin_leggings", () -> new ArmorItem(MWArmorMaterials.TIN_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> TIN_BOOTS = ITEMS.register("tin_boots", () -> new ArmorItem(MWArmorMaterials.TIN_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> BRONZE_HELMET = ITEMS.register("bronze_helmet", () -> new ArmorItem(MWArmorMaterials.BRONZE_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> BRONZE_CHESTPLATE = ITEMS.register("bronze_chestplate", () -> new ArmorItem(MWArmorMaterials.BRONZE_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> BRONZE_LEGGINGS = ITEMS.register("bronze_leggings", () -> new ArmorItem(MWArmorMaterials.BRONZE_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> BRONZE_BOOTS = ITEMS.register("bronze_boots", () -> new ArmorItem(MWArmorMaterials.BRONZE_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> WARRIOR_HELMET = ITEMS.register("warrior_helmet", () -> new WarriorArmorItem(MWArmorMaterials.WARRIOR_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> WARRIOR_CHESTPLATE = ITEMS.register("warrior_chestplate", () -> new WarriorArmorItem(MWArmorMaterials.WARRIOR_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> WARRIOR_LEGGINGS = ITEMS.register("warrior_leggings", () -> new WarriorArmorItem(MWArmorMaterials.WARRIOR_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> WARRIOR_BOOTS = ITEMS.register("warrior_boots", () -> new WarriorArmorItem(MWArmorMaterials.WARRIOR_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> IRON_KNIGHT_HELMET = ITEMS.register("iron_knight_helmet", () -> new KnightArmorHelmetItem(MWArmorMaterials.IRON_KNIGHT_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab), "iron"));
    public static final RegistryObject<Item> IRON_KNIGHT_CHESTPLATE = ITEMS.register("iron_knight_chestplate", () -> new KnightArmorChestplateItem(MWArmorMaterials.IRON_KNIGHT_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab), "iron"));
    public static final RegistryObject<Item> IRON_KNIGHT_LEGGINGS = ITEMS.register("iron_knight_leggings", () -> new KnightArmorLeggingsItem(MWArmorMaterials.IRON_KNIGHT_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab), "iron"));
    public static final RegistryObject<Item> IRON_KNIGHT_BOOTS = ITEMS.register("iron_knight_boots", () -> new KnightArmorBootsItem(MWArmorMaterials.IRON_KNIGHT_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab), "iron"));

    public static final RegistryObject<Item> SILVER_KNIGHT_HELMET = ITEMS.register("silver_knight_helmet", () -> new KnightArmorHelmetItem(MWArmorMaterials.SILVER_KNIGHT_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab), "silver"));
    public static final RegistryObject<Item> SILVER_KNIGHT_CHESTPLATE = ITEMS.register("silver_knight_chestplate", () -> new KnightArmorChestplateItem(MWArmorMaterials.SILVER_KNIGHT_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab), "silver"));
    public static final RegistryObject<Item> SILVER_KNIGHT_LEGGINGS = ITEMS.register("silver_knight_leggings", () -> new KnightArmorLeggingsItem(MWArmorMaterials.SILVER_KNIGHT_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab), "silver"));
    public static final RegistryObject<Item> SILVER_KNIGHT_BOOTS = ITEMS.register("silver_knight_boots", () -> new KnightArmorBootsItem(MWArmorMaterials.SILVER_KNIGHT_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab), "silver"));

    public static final RegistryObject<Item> STEEL_KNIGHT_HELMET = ITEMS.register("steel_knight_helmet", () -> new KnightArmorHelmetItem(MWArmorMaterials.STEEL_KNIGHT_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab), "steel"));
    public static final RegistryObject<Item> STEEL_KNIGHT_CHESTPLATE = ITEMS.register("steel_knight_chestplate", () -> new KnightArmorChestplateItem(MWArmorMaterials.STEEL_KNIGHT_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab), "steel"));
    public static final RegistryObject<Item> STEEL_KNIGHT_LEGGINGS = ITEMS.register("steel_knight_leggings", () -> new KnightArmorLeggingsItem(MWArmorMaterials.STEEL_KNIGHT_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab), "steel"));
    public static final RegistryObject<Item> STEEL_KNIGHT_BOOTS = ITEMS.register("steel_knight_boots", () -> new KnightArmorBootsItem(MWArmorMaterials.STEEL_KNIGHT_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab), "steel"));

    public static final RegistryObject<Item> NETHERITE_KNIGHT_HELMET = ITEMS.register("netherite_knight_helmet", () -> new KnightArmorHelmetItem(MWArmorMaterials.NETHERITE_KNIGHT_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab).fireResistant(), "netherite"));
    public static final RegistryObject<Item> NETHERITE_KNIGHT_CHESTPLATE = ITEMS.register("netherite_knight_chestplate", () -> new KnightArmorChestplateItem(MWArmorMaterials.NETHERITE_KNIGHT_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab).fireResistant(), "netherite"));
    public static final RegistryObject<Item> NETHERITE_KNIGHT_LEGGINGS = ITEMS.register("netherite_knight_leggings", () -> new KnightArmorLeggingsItem(MWArmorMaterials.NETHERITE_KNIGHT_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab).fireResistant(), "netherite"));
    public static final RegistryObject<Item> NETHERITE_KNIGHT_BOOTS = ITEMS.register("netherite_knight_boots", () -> new KnightArmorBootsItem(MWArmorMaterials.NETHERITE_KNIGHT_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab).fireResistant(), "netherite"));

    public static final RegistryObject<Item> SILVER_HELMET = ITEMS.register("silver_helmet", () -> new ArmorItem(MWArmorMaterials.SILVER_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_CHESTPLATE = ITEMS.register("silver_chestplate", () -> new ArmorItem(MWArmorMaterials.SILVER_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_LEGGINGS = ITEMS.register("silver_leggings", () -> new ArmorItem(MWArmorMaterials.SILVER_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> SILVER_BOOTS = ITEMS.register("silver_boots", () -> new ArmorItem(MWArmorMaterials.SILVER_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> STEEL_HELMET = ITEMS.register("steel_helmet", () -> new ArmorItem(MWArmorMaterials.STEEL_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_CHESTPLATE = ITEMS.register("steel_chestplate", () -> new ArmorItem(MWArmorMaterials.STEEL_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_LEGGINGS = ITEMS.register("steel_leggings", () -> new ArmorItem(MWArmorMaterials.STEEL_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<Item> STEEL_BOOTS = ITEMS.register("steel_boots", () -> new ArmorItem(MWArmorMaterials.STEEL_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> GAMBESON_CHESTPLATE = ITEMS.register("gambeson_chestplate", () -> new GambesonChestplateItem(new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<Item> SILVER_HORSE_ARMOR = ITEMS.register("silver_horse_armor", () -> new CustomHorseArmorItem(6, new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/horse/armor/horse_armor_netherite.png"), (new Item.Properties()).stacksTo(1).tab(MedievalWorlds.tab).fireResistant()));
    public static final RegistryObject<Item> NETHERITE_HORSE_ARMOR = ITEMS.register("netherite_horse_armor", () -> new CustomHorseArmorItem(15, new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/horse/armor/horse_armor_netherite.png"), (new Item.Properties()).stacksTo(1).tab(MedievalWorlds.tab).fireResistant()));

    public static final RegistryObject<Item> CROWN = ITEMS.register("crown", () -> new CrownItem(ArmorMaterials.GOLD, new Item.Properties().tab(MedievalWorlds.tab)));

}