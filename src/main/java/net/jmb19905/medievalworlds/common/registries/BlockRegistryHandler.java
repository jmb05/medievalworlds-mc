package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.block.AbstractOreBlock;
import net.jmb19905.medievalworlds.common.block.AlloyFurnaceBlock;
import net.jmb19905.medievalworlds.common.block.BloomeryBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistryHandler {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MedievalWorlds.MOD_ID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MedievalWorlds.MOD_ID);

    //Blocks
    public static final RegistryObject<Block> BRONZE_BLOCK = BLOCKS.register("bronze_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(3,3).sound(SoundType.METAL)));
    public static final RegistryObject<Block> TIN_BLOCK = BLOCKS.register("tin_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(3,3).sound(SoundType.METAL)));
    public static final RegistryObject<Block> RUBY_BLOCK = BLOCKS.register("ruby_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5,3).sound(SoundType.METAL)));
    public static final RegistryObject<Block> STEEL_BLOCK = BLOCKS.register("steel_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5,3).sound(SoundType.METAL)));
    public static final RegistryObject<Block> SILVER_BLOCK = BLOCKS.register("silver_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5,3).sound(SoundType.METAL)));

    public static final RegistryObject<Block> TIN_ORE_BLOCK = BLOCKS.register("tin_ore", () -> new AbstractOreBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3).sound(SoundType.STONE),0));
    public static final RegistryObject<Block> RUBY_ORE_BLOCK = BLOCKS.register("ruby_ore", () -> new AbstractOreBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3).sound(SoundType.STONE), 0, 3));
    public static final RegistryObject<Block> SILVER_ORE_BLOCK = BLOCKS.register("silver_ore", () -> new AbstractOreBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3).sound(SoundType.STONE), 0));

    public static final RegistryObject<Block> ALLOY_FURNACE_BLOCK = BLOCKS.register("alloy_furnace", AlloyFurnaceBlock::new);

    public static final RegistryObject<Block> BLOOMERY_BOTTOM_BLOCK = BLOCKS.register("bloomery_bottom", BloomeryBlock.Bottom::new);
    public static final RegistryObject<Block> BLOOMERY_TOP_BLOCK = BLOCKS.register("bloomery_top", BloomeryBlock.Top::new);

    public static final RegistryObject<Block> LIMESTONE_BLOCK = BLOCKS.register("limestone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5f, 3).sound(SoundType.STONE)));
    public static final RegistryObject<Block> LIMESTONE_BRICKS_BLOCK = BLOCKS.register("limestone_bricks", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5f, 3.5f).sound(SoundType.STONE)));


    //BlockItems
    public static final RegistryObject<BlockItem> BRONZE_BLOCK_ITEM = BLOCK_ITEMS.register("bronze_block", () -> new BlockItem(BRONZE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab))) ;
    public static final RegistryObject<BlockItem> TIN_BLOCK_ITEM = BLOCK_ITEMS.register("tin_block", () -> new BlockItem(TIN_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab))) ;
    public static final RegistryObject<BlockItem> RUBY_BLOCK_ITEM = BLOCK_ITEMS.register("ruby_block", () -> new BlockItem(RUBY_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab))) ;
    public static final RegistryObject<BlockItem> STEEL_BLOCK_ITEM = BLOCK_ITEMS.register("steel_block", () -> new BlockItem(STEEL_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab))) ;
    public static final RegistryObject<BlockItem> SILVER_BLOCK_ITEM = BLOCK_ITEMS.register("silver_block", () -> new BlockItem(SILVER_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab))) ;

    public static final RegistryObject<BlockItem> TIN_ORE_BLOCK_ITEM = BLOCK_ITEMS.register("tin_ore", () -> new BlockItem(TIN_ORE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<BlockItem> RUBY_ORE_BLOCK_ITEM = BLOCK_ITEMS.register("ruby_ore", () -> new BlockItem(RUBY_ORE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<BlockItem> SILVER_ORE_BLOCK_ITEM = BLOCK_ITEMS.register("silver_ore", () -> new BlockItem(SILVER_ORE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<BlockItem> ALLOY_FURNACE_BLOCK_ITEM = BLOCK_ITEMS.register("alloy_furnace", () -> new BlockItem(ALLOY_FURNACE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<BlockItem> BLOOMERY_ITEM = BLOCK_ITEMS.register("bloomery_bottom", () -> new BlockItem(BLOOMERY_BOTTOM_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<BlockItem> LIMESTONE_ITEM = BLOCK_ITEMS.register("limestone", () -> new BlockItem(LIMESTONE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<BlockItem> LIMESTONE_BRICKS_ITEM = BLOCK_ITEMS.register("limestone_bricks", () -> new BlockItem(LIMESTONE_BRICKS_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab)));

}
