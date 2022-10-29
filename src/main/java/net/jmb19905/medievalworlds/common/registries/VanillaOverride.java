package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.common.block.CustomAnvilBlock;
import net.jmb19905.medievalworlds.common.block.CustomFletchingTable;
import net.jmb19905.medievalworlds.common.block.CustomSmithingTable;
import net.jmb19905.medievalworlds.common.item.MWTiers;
import net.jmb19905.medievalworlds.common.item.armor.CustomHorseArmorItem;
import net.jmb19905.medievalworlds.common.item.armor.MWArmorMaterials;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VanillaOverride {

    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    public static final RegistryObject<Block> DAMAGED_ANVIL = BLOCKS.register("damaged_anvil", () -> new CustomAnvilBlock(null, .01f, BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.ANVIL)));
    public static final RegistryObject<Block> CHIPPED_ANVIL = BLOCKS.register("chipped_anvil", () -> new CustomAnvilBlock(DAMAGED_ANVIL.get().defaultBlockState(), .01f, BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.ANVIL)));
    public static final RegistryObject<Block> ANVIL = BLOCKS.register("anvil", () -> new CustomAnvilBlock(CHIPPED_ANVIL.get().defaultBlockState(), .01f, BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.ANVIL)));
    public static final RegistryObject<Block> SMITHING_TABLE = BLOCKS.register("smithing_table", () -> new CustomSmithingTable(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FLETCHING_TABLE = BLOCKS.register("fletching_table", () -> new CustomFletchingTable(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD)));

    public static final RegistryObject<BlockItem> ANVIL_ITEM = ITEMS.register("anvil", () -> new BlockItem(ANVIL.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> CHIPPED_ANVIL_ITEM = ITEMS.register("chipped_anvil", () -> new BlockItem(CHIPPED_ANVIL.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> DAMAGED_ANVIL_ITEM = ITEMS.register("damaged_anvil", () -> new BlockItem(DAMAGED_ANVIL.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> SMITHING_TABLE_ITEM = ITEMS.register("smithing_table", () -> new BlockItem(SMITHING_TABLE.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> FLETCHING_TABLE_ITEM = ITEMS.register("fletching_table", () -> new BlockItem(FLETCHING_TABLE.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final RegistryObject<Item> GOLDEN_HORSE_ARMOR = ITEMS.register("golden_horse_armor", () -> new CustomHorseArmorItem(5, "gold", (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> IRON_HORSE_ARMOR = ITEMS.register("iron_horse_armor", () -> new CustomHorseArmorItem(7, "iron", (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> STONE_SWORD = ITEMS.register("stone_sword", () -> new SwordItem(MWTiers.CUSTOM_STONE, 3, -2.4F, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> STONE_SHOVEL = ITEMS.register("stone_shovel", () -> new ShovelItem(MWTiers.CUSTOM_STONE, 1.5F, -3.0F, (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> STONE_PICKAXE = ITEMS.register("stone_pickaxe", () -> new PickaxeItem(MWTiers.CUSTOM_STONE, 1, -2.8F, (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> STONE_AXE = ITEMS.register("stone_axe", () -> new AxeItem(MWTiers.CUSTOM_STONE, 7.0F, -3.2F, (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> STONE_HOE = ITEMS.register("stone_hoe", () -> new HoeItem(MWTiers.CUSTOM_STONE, -1, -2.0F, (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> DIAMOND_SWORD = ITEMS.register("diamond_sword", () -> new SwordItem(MWTiers.CUSTOM_DIAMOND, 3, -2.4F, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> DIAMOND_SHOVEL = ITEMS.register("diamond_shovel", () -> new ShovelItem(MWTiers.CUSTOM_DIAMOND, 1.5F, -3.0F, (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> DIAMOND_PICKAXE = ITEMS.register("diamond_pickaxe", () -> new PickaxeItem(MWTiers.CUSTOM_DIAMOND, 1, -2.8F, (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> DIAMOND_AXE = ITEMS.register("diamond_axe", () -> new AxeItem(MWTiers.CUSTOM_DIAMOND, 5.0F, -3.0F, (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> DIAMOND_HOE = ITEMS.register("diamond_hoe", () -> new HoeItem(MWTiers.CUSTOM_DIAMOND, -3, 0.0F, (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> DIAMOND_HELMET = ITEMS.register("diamond_helmet", () -> new ArmorItem(MWArmorMaterials.CUSTOM_DIAMOND, EquipmentSlot.HEAD, (new Item.Properties().tab(CreativeModeTab.TAB_COMBAT))));
    public static final RegistryObject<Item> DIAMOND_CHESTPLATE = ITEMS.register("diamond_chestplate", () -> new ArmorItem(MWArmorMaterials.CUSTOM_DIAMOND, EquipmentSlot.CHEST, (new Item.Properties().tab(CreativeModeTab.TAB_COMBAT))));
    public static final RegistryObject<Item> DIAMOND_LEGGINGS = ITEMS.register("diamond_leggings", () -> new ArmorItem(MWArmorMaterials.CUSTOM_DIAMOND, EquipmentSlot.LEGS, (new Item.Properties().tab(CreativeModeTab.TAB_COMBAT))));
    public static final RegistryObject<Item> DIAMOND_BOOTS = ITEMS.register("diamond_boots", () -> new ArmorItem(MWArmorMaterials.CUSTOM_DIAMOND, EquipmentSlot.FEET, (new Item.Properties().tab(CreativeModeTab.TAB_COMBAT))));

}