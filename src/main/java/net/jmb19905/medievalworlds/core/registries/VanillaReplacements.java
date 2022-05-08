package net.jmb19905.medievalworlds.core.registries;

import net.jmb19905.medievalworlds.common.block.CustomFireBlock;
import net.jmb19905.medievalworlds.common.block.CustomFletchingTableBlock;
import net.jmb19905.medievalworlds.common.block.anvil.CustomAnvilBlock;
import net.jmb19905.medievalworlds.common.block.CustomSmithingTable;
import net.jmb19905.medievalworlds.common.item.armor.CustomHorseArmorItem;
import net.jmb19905.medievalworlds.common.item.tiers.MWItemTiers;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VanillaReplacements {

    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    public static final RegistryObject<Block> DAMAGED_ANVIL = BLOCKS.register("damaged_anvil", () -> new CustomAnvilBlock(null, .03f, BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.ANVIL)));
    public static final RegistryObject<Block> CHIPPED_ANVIL = BLOCKS.register("chipped_anvil", () -> new CustomAnvilBlock(DAMAGED_ANVIL.get().defaultBlockState(), .03f, BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.ANVIL)));
    public static final RegistryObject<Block> ANVIL = BLOCKS.register("anvil", () -> new CustomAnvilBlock(CHIPPED_ANVIL.get().defaultBlockState(), .03f, BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.ANVIL)));
    public static final RegistryObject<Block> SMITHING_TABLE = BLOCKS.register("smithing_table", () -> new CustomSmithingTable(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> FLETCHING_TABLE = BLOCKS.register("fletching_table", () -> new CustomFletchingTableBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> FIRE = BLOCKS.register("fire", () -> new CustomFireBlock(BlockBehaviour.Properties.of(Material.FIRE, MaterialColor.FIRE).noCollission().instabreak().lightLevel((p_152605_) -> 15).sound(SoundType.WOOL)));

    public static final RegistryObject<BlockItem> ANVIL_ITEM = ITEMS.register("anvil", () -> new BlockItem(ANVIL.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> CHIPPED_ANVIL_ITEM = ITEMS.register("chipped_anvil", () -> new BlockItem(CHIPPED_ANVIL.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> DAMAGED_ANVIL_ITEM = ITEMS.register("damaged_anvil", () -> new BlockItem(DAMAGED_ANVIL.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> SMITHING_TABLE_ITEM = ITEMS.register("smithing_table", () -> new BlockItem(SMITHING_TABLE.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> FLETCHING_TABLE_ITEM = ITEMS.register("fletching_table", () -> new BlockItem(FLETCHING_TABLE.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final RegistryObject<Item> GOLDEN_HORSE_ARMOR = ITEMS.register("golden_horse_armor", () -> new CustomHorseArmorItem(5, "gold", (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> IRON_HORSE_ARMOR = ITEMS.register("iron_horse_armor", () -> new CustomHorseArmorItem(7, "iron", (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> STONE_SWORD = ITEMS.register("stone_sword", () -> new SwordItem(MWItemTiers.CUSTOM_STONE_TIER, 3, -2.4F, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> STONE_SHOVEL = ITEMS.register("stone_shovel", () -> new ShovelItem(MWItemTiers.CUSTOM_STONE_TIER, 1.5F, -3.0F, (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> STONE_PICKAXE = ITEMS.register("stone_pickaxe", () -> new PickaxeItem(MWItemTiers.CUSTOM_STONE_TIER, 1, -2.8F, (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> STONE_AXE = ITEMS.register("stone_axe", () -> new AxeItem(MWItemTiers.CUSTOM_STONE_TIER, 7.0F, -3.2F, (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> STONE_HOE = ITEMS.register("stone_hoe", () -> new HoeItem(MWItemTiers.CUSTOM_STONE_TIER, -1, -2.0F, (new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS)));

}