package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.block.AbstractOreBlock;
import net.jmb19905.medievalworlds.common.block.AlloyFurnaceBlock;
import net.jmb19905.medievalworlds.common.block.anvil.CylindricalAnvil;
import net.jmb19905.medievalworlds.common.block.forge.ForgeBaseBlock;
import net.jmb19905.medievalworlds.common.block.slackTub.SlackTubBlock;
import net.jmb19905.medievalworlds.common.block.bloomery.BloomeryBlockBottom;
import net.jmb19905.medievalworlds.common.block.bloomery.BloomeryBlockTop;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MWBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MedievalWorlds.MOD_ID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MedievalWorlds.MOD_ID);

    //Blocks
    public static final RegistryObject<Block> BRONZE_BLOCK = BLOCKS.register("bronze_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(3.5f,6.0f).sound(SoundType.METAL)));
    public static final RegistryObject<Block> TIN_BLOCK = BLOCKS.register("tin_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops().strength(2.5f,6.0f).sound(SoundType.METAL)));
    public static final RegistryObject<Block> RUBY_BLOCK = BLOCKS.register("ruby_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(5,6.0f).sound(SoundType.METAL)));
    public static final RegistryObject<Block> STEEL_BLOCK = BLOCKS.register("steel_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(5,6.0f).sound(SoundType.METAL)));
    public static final RegistryObject<Block> SILVER_BLOCK = BLOCKS.register("silver_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.QUARTZ).requiresCorrectToolForDrops().strength(5,3).sound(SoundType.METAL)));
    //public static final RegistryObject<Block> LEAD_BLOCK = BLOCKS.register("lead_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(3,3).sound(SoundType.METAL)));

    public static final RegistryObject<Block> TIN_ORE_BLOCK = BLOCKS.register("tin_ore", () -> new AbstractOreBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3).sound(SoundType.STONE),0));
    public static final RegistryObject<Block> RUBY_ORE_BLOCK = BLOCKS.register("ruby_ore", () -> new AbstractOreBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3).sound(SoundType.STONE), 0, 3));
    public static final RegistryObject<Block> SILVER_ORE_BLOCK = BLOCKS.register("silver_ore", () -> new AbstractOreBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3).sound(SoundType.STONE), 0));
    //public static final RegistryObject<Block> LEAD_ORE_BLOCK = BLOCKS.register("lead_ore", () -> new AbstractOreBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3).sound(SoundType.STONE), 0));

    public static final RegistryObject<Block> DEEPSLATE_TIN_ORE_BLOCK = BLOCKS.register("deepslate_tin_ore", () -> new AbstractOreBlock(BlockBehaviour.Properties.copy(TIN_ORE_BLOCK.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE),0));
    public static final RegistryObject<Block> DEEPSLATE_RUBY_ORE_BLOCK = BLOCKS.register("deepslate_ruby_ore", () -> new AbstractOreBlock(BlockBehaviour.Properties.copy(RUBY_ORE_BLOCK.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE), 3, 7));
    public static final RegistryObject<Block> DEEPSLATE_SILVER_ORE_BLOCK = BLOCKS.register("deepslate_silver_ore", () -> new AbstractOreBlock(BlockBehaviour.Properties.copy(SILVER_ORE_BLOCK.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE), 0));

    public static final RegistryObject<Block> RAW_TIN_BLOCK = BLOCKS.register("raw_tin_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final RegistryObject<Block> RAW_SILVER_BLOCK = BLOCKS.register("raw_silver_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));

    public static final RegistryObject<Block> ALLOY_FURNACE_BLOCK = BLOCKS.register("alloy_furnace", () -> new AlloyFurnaceBlock(Block.Properties.of(Material.STONE, MaterialColor.COLOR_RED).strength(3.5F).lightLevel(value -> 13)));

    public static final RegistryObject<Block> BLOOMERY_BOTTOM_BLOCK = BLOCKS.register("bloomery_bottom", () -> new BloomeryBlockBottom(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_ORANGE).sound(SoundType.STONE).strength(3.5F).lightLevel(value -> 13)));
    public static final RegistryObject<Block> BLOOMERY_TOP_BLOCK = BLOCKS.register("bloomery_top", () -> new BloomeryBlockTop(BlockBehaviour.Properties.copy(BLOOMERY_BOTTOM_BLOCK.get())));

    public static final RegistryObject<Block> FORGE_BASE_BLOCK = BLOCKS.register("forge_base", () -> new ForgeBaseBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));

    public static final RegistryObject<Block> LIMESTONE_BLOCK = BLOCKS.register("limestone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 3).sound(SoundType.STONE)));
    public static final RegistryObject<Block> LIMESTONE_BRICKS_BLOCK = BLOCKS.register("limestone_bricks", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 3.5f).sound(SoundType.STONE)));

    public static final RegistryObject<SlackTubBlock> SLACK_TUB_BLOCK = BLOCKS.register("slack_tub", () -> new SlackTubBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<CylindricalAnvil> STONE_ANVIL = BLOCKS.register("stone_anvil", () -> new CylindricalAnvil(BlockBehaviour.Properties.of(Material.STONE).strength(3.0f).sound(SoundType.STONE)));

    //BlockItems
    public static final RegistryObject<BlockItem> BRONZE_BLOCK_ITEM = BLOCK_ITEMS.register("bronze_block", () -> new BlockItem(BRONZE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab))) ;
    public static final RegistryObject<BlockItem> TIN_BLOCK_ITEM = BLOCK_ITEMS.register("tin_block", () -> new BlockItem(TIN_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab))) ;
    public static final RegistryObject<BlockItem> RUBY_BLOCK_ITEM = BLOCK_ITEMS.register("ruby_block", () -> new BlockItem(RUBY_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab))) ;
    public static final RegistryObject<BlockItem> STEEL_BLOCK_ITEM = BLOCK_ITEMS.register("steel_block", () -> new BlockItem(STEEL_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab))) ;
    public static final RegistryObject<BlockItem> SILVER_BLOCK_ITEM = BLOCK_ITEMS.register("silver_block", () -> new BlockItem(SILVER_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab))) ;

    public static final RegistryObject<BlockItem> TIN_ORE_BLOCK_ITEM = BLOCK_ITEMS.register("tin_ore", () -> new BlockItem(TIN_ORE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<BlockItem> RUBY_ORE_BLOCK_ITEM = BLOCK_ITEMS.register("ruby_ore", () -> new BlockItem(RUBY_ORE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<BlockItem> SILVER_ORE_BLOCK_ITEM = BLOCK_ITEMS.register("silver_ore", () -> new BlockItem(SILVER_ORE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<BlockItem> RAW_TIN_BLOCK_ITEM = BLOCK_ITEMS.register("raw_tin_block", () -> new BlockItem(RAW_TIN_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<BlockItem> RAW_SILVER_BLOCK_ITEM = BLOCK_ITEMS.register("raw_silver_block", () -> new BlockItem(RAW_SILVER_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<BlockItem> ALLOY_FURNACE_BLOCK_ITEM = BLOCK_ITEMS.register("alloy_furnace", () -> new BlockItem(ALLOY_FURNACE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<BlockItem> BLOOMERY_ITEM = BLOCK_ITEMS.register("bloomery", () -> new BlockItem(BLOOMERY_BOTTOM_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<BlockItem> LIMESTONE_ITEM = BLOCK_ITEMS.register("limestone", () -> new BlockItem(LIMESTONE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<BlockItem> LIMESTONE_BRICKS_ITEM = BLOCK_ITEMS.register("limestone_bricks", () -> new BlockItem(LIMESTONE_BRICKS_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab)));

    public static final RegistryObject<BlockItem> SLACK_TUB = BLOCK_ITEMS.register("slack_tub", () -> new BlockItem(SLACK_TUB_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.tab)));
    public static final RegistryObject<BlockItem> STONE_ANVIL_ITEM = BLOCK_ITEMS.register("stone_anvil", () -> new BlockItem(STONE_ANVIL.get(), new Item.Properties().tab(MedievalWorlds.tab)));

}
