package net.jmb19905.medievalworlds.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.block.AbstractOreBlock;
import net.jmb19905.medievalworlds.block.AlloyFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistryHandler {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MedievalWorlds.MOD_ID);
    public static final DeferredRegister<net.minecraft.item.Item> BLOCK_ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MedievalWorlds.MOD_ID);

    //Blocks
    public static final RegistryObject<Block> BRONZE_BLOCK = BLOCKS.register("bronze_block", () -> new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(3,6).sound(SoundType.METAL)));
    public static final RegistryObject<Block> COPPER_BLOCK = BLOCKS.register("copper_block", () -> new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(3,6).sound(SoundType.METAL)));
    public static final RegistryObject<Block> TIN_BLOCK = BLOCKS.register("tin_block", () -> new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(3,6).sound(SoundType.METAL)));
    public static final RegistryObject<Block> RUBY_BLOCK = BLOCKS.register("ruby_block", () -> new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5,6).sound(SoundType.METAL)));
    public static final RegistryObject<Block> STEEL_BLOCK = BLOCKS.register("steel_block", () -> new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5,6).sound(SoundType.METAL)));
    public static final RegistryObject<Block> SILVER_BLOCK = BLOCKS.register("silver_block", () -> new Block(Block.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5,6).sound(SoundType.METAL)));

    public static final RegistryObject<Block> COPPER_ORE_BLOCK = BLOCKS.register("copper_ore", () -> new AbstractOreBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(3).sound(SoundType.STONE), 0));
    public static final RegistryObject<Block> TIN_ORE_BLOCK = BLOCKS.register("tin_ore", () -> new AbstractOreBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(3).sound(SoundType.STONE),0));
    public static final RegistryObject<Block> RUBY_ORE_BLOCK = BLOCKS.register("ruby_ore", () -> new AbstractOreBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3).sound(SoundType.STONE), 0, 3));
    public static final RegistryObject<Block> SILVER_ORE_BLOCK = BLOCKS.register("silver_ore", () -> new AbstractOreBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3).sound(SoundType.STONE), 0));

    public static final RegistryObject<Block> ALLOY_FURNACE_BLOCK = BLOCKS.register("alloy_furnace", AlloyFurnaceBlock::new);

    //BlockItems
    public static final RegistryObject<BlockItem> BRONZE_BLOCK_ITEM = BLOCK_ITEMS.register("bronze_block", () -> new BlockItem(BRONZE_BLOCK.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))) ;
    public static final RegistryObject<BlockItem> COPPER_BLOCK_ITEM = BLOCK_ITEMS.register("copper_block", () -> new BlockItem(COPPER_BLOCK.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))) ;
    public static final RegistryObject<BlockItem> TIN_BLOCK_ITEM = BLOCK_ITEMS.register("tin_block", () -> new BlockItem(TIN_BLOCK.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))) ;
    public static final RegistryObject<BlockItem> RUBY_BLOCK_ITEM = BLOCK_ITEMS.register("ruby_block", () -> new BlockItem(RUBY_BLOCK.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))) ;
    public static final RegistryObject<BlockItem> STEEL_BLOCK_ITEM = BLOCK_ITEMS.register("steel_block", () -> new BlockItem(STEEL_BLOCK.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))) ;
    public static final RegistryObject<BlockItem> SILVER_BLOCK_ITEM = BLOCK_ITEMS.register("silver_block", () -> new BlockItem(SILVER_BLOCK.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))) ;

    public static final RegistryObject<BlockItem> COPPER_ORE_BLOCK_ITEM = BLOCK_ITEMS.register("copper_ore", () -> new BlockItem(COPPER_ORE_BLOCK.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> TIN_ORE_BLOCK_ITEM = BLOCK_ITEMS.register("tin_ore", () -> new BlockItem(TIN_ORE_BLOCK.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> RUBY_ORE_BLOCK_ITEM = BLOCK_ITEMS.register("ruby_ore", () -> new BlockItem(RUBY_ORE_BLOCK.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> SILVER_ORE_BLOCK_ITEM = BLOCK_ITEMS.register("silver_ore", () -> new BlockItem(SILVER_ORE_BLOCK.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistryObject<BlockItem> ALLOY_FURNACE_BLOCK_ITEM = BLOCK_ITEMS.register("alloy_furnace", () -> new BlockItem(ALLOY_FURNACE_BLOCK.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
}
