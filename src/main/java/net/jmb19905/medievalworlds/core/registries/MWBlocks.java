package net.jmb19905.medievalworlds.core.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.block.*;
import net.jmb19905.medievalworlds.common.block.anvil.CylindricalAnvil;
import net.jmb19905.medievalworlds.common.block.SimpleBloomery;
import net.jmb19905.medievalworlds.common.block.forge.ForgeControllerBlock;
import net.jmb19905.medievalworlds.common.block.SlackTubBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class MWBlocks {

    public static List<BlockItem> blockItemOrder;
    private static final List<RegistryObject<BlockItem>> blockItemRegistryOrder = new ArrayList<>();

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

    public static final RegistryObject<Block> BLOOMERY = BLOCKS.register("bloomery", () -> new SimpleBloomery(BlockBehaviour.Properties.of(Material.CLAY, MaterialColor.CLAY).sound(SoundType.GRAVEL).strength(0.6F).noOcclusion()));
    public static final RegistryObject<Block> BRICK_BLOOMERY = BLOCKS.register("brick_bloomery", () -> new AdvancedBloomery(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).sound(SoundType.STONE).strength(2)));
    public static final RegistryObject<BellowsBlock> BELLOWS = BLOCKS.register("bellows", () -> new BellowsBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).sound(SoundType.WOOD).strength(0.6f).noOcclusion()));

    public static final RegistryObject<Block> FORGE_BLOCK = BLOCKS.register("forge", () -> new ForgeControllerBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));

    public static final RegistryObject<Block> LIMESTONE_BLOCK = BLOCKS.register("limestone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 3).sound(SoundType.STONE)));
    public static final RegistryObject<Block> LIMESTONE_BRICKS_BLOCK = BLOCKS.register("limestone_bricks", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.SAND).requiresCorrectToolForDrops().strength(1.5f, 3.5f).sound(SoundType.STONE)));

    public static final RegistryObject<SlackTubBlock> SLACK_TUB_BLOCK = BLOCKS.register("slack_tub", () -> new SlackTubBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<CylindricalAnvil> STONE_ANVIL = BLOCKS.register("stone_anvil", () -> new CylindricalAnvil(null, .07f, BlockBehaviour.Properties.of(Material.STONE).strength(3.0f).sound(SoundType.STONE)));

    public static final RegistryObject<RotatedPillarBlock> CHARCOAL_LOG = BLOCKS.register("charcoal_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> CHARCOAl_PLANKS = BLOCKS.register("charcoal_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0f).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> BRICK_CHIMNEY = BLOCKS.register("brick_chimney", () -> new ChimneyBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS)));

    public static final RegistryObject<Block> QUERN = BLOCKS.register("quern", () -> new QuernBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_STONE).noOcclusion()));

    //BlockItems
    public static final RegistryObject<BlockItem> TIN_BLOCK_ITEM = registerBlockItem("tin_block", () -> new BlockItem(TIN_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab))) ;
    public static final RegistryObject<BlockItem> BRONZE_BLOCK_ITEM = registerBlockItem("bronze_block", () -> new BlockItem(BRONZE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab))) ;
    public static final RegistryObject<BlockItem> STEEL_BLOCK_ITEM = registerBlockItem("steel_block", () -> new BlockItem(STEEL_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab))) ;
    public static final RegistryObject<BlockItem> SILVER_BLOCK_ITEM = registerBlockItem("silver_block", () -> new BlockItem(SILVER_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab))) ;
    public static final RegistryObject<BlockItem> RUBY_BLOCK_ITEM = registerBlockItem("ruby_block", () -> new BlockItem(RUBY_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab))) ;

    public static final RegistryObject<BlockItem> TIN_ORE_BLOCK_ITEM = registerBlockItem("tin_ore", () -> new BlockItem(TIN_ORE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> SILVER_ORE_BLOCK_ITEM = registerBlockItem("silver_ore", () -> new BlockItem(SILVER_ORE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> RUBY_ORE_BLOCK_ITEM = registerBlockItem("ruby_ore", () -> new BlockItem(RUBY_ORE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    public static final RegistryObject<BlockItem> DEEPSLATE_TIN_ORE_BLOCK_ITEM = registerBlockItem("deepslate_tin_ore", () -> new BlockItem(DEEPSLATE_TIN_ORE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> DEEPSLATE_SILVER_ORE_BLOCK_ITEM = registerBlockItem("deepslate_silver_ore", () -> new BlockItem(DEEPSLATE_SILVER_ORE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> DEEPSLATE_RUBY_ORE_BLOCK_ITEM = registerBlockItem("deepslate_ruby_ore", () -> new BlockItem(DEEPSLATE_RUBY_ORE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    public static final RegistryObject<BlockItem> RAW_TIN_BLOCK_ITEM = registerBlockItem("raw_tin_block", () -> new BlockItem(RAW_TIN_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> RAW_SILVER_BLOCK_ITEM = registerBlockItem("raw_silver_block", () -> new BlockItem(RAW_SILVER_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    public static final RegistryObject<BlockItem> ALLOY_FURNACE_BLOCK_ITEM = registerBlockItem("alloy_furnace", () -> new BlockItem(ALLOY_FURNACE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    public static final RegistryObject<BlockItem> BLOOMERY_ITEM = registerBlockItem("bloomery", () -> new BlockItem(BLOOMERY.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> BRICK_BLOOMERY_ITEM = registerBlockItem("brick_bloomery", () -> new BlockItem(BRICK_BLOOMERY.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> BELLOWS_ITEM = registerBlockItem("bellows", () -> new BlockItem(BELLOWS.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    public static final RegistryObject<BlockItem> LIMESTONE_ITEM = registerBlockItem("limestone", () -> new BlockItem(LIMESTONE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> LIMESTONE_BRICKS_ITEM = registerBlockItem("limestone_bricks", () -> new BlockItem(LIMESTONE_BRICKS_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    public static final RegistryObject<BlockItem> SLACK_TUB = registerBlockItem("slack_tub", () -> new BlockItem(SLACK_TUB_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> STONE_ANVIL_ITEM = registerBlockItem("stone_anvil", () -> new BlockItem(STONE_ANVIL.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    public static final RegistryObject<BlockItem> CHARCOAL_LOG_ITEM = registerBlockItem("charcoal_log", () -> new BlockItem(CHARCOAL_LOG.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> CHARCOAL_PLANKS_ITEM = registerBlockItem("charcoal_planks", () -> new BlockItem(CHARCOAl_PLANKS.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    public static final RegistryObject<BlockItem> BRICK_CHIMNEY_ITEM = registerBlockItem("brick_chimney", () -> new BlockItem(BRICK_CHIMNEY.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    public static final RegistryObject<BlockItem> QUERN_ITEM = registerBlockItem("quern", () -> new BlockItem(QUERN.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    private static RegistryObject<BlockItem> registerBlockItem(String id, Supplier<BlockItem> itemSupplier) {
        RegistryObject<BlockItem> regObj = BLOCK_ITEMS.register(id, itemSupplier);
        blockItemRegistryOrder.add(regObj);
        return regObj;
    }

    public static List<BlockItem> getBlockItemTabOrder() {
        if(blockItemOrder == null) {
            blockItemOrder = blockItemRegistryOrder.stream().collect(ArrayList::new, (list, regObj) -> list.add(regObj.get()), ArrayList::addAll);
        }
        return blockItemOrder;
    }
}
