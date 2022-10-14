package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.block.CharcoalLogBlock;
import net.jmb19905.medievalworlds.common.block.CharcoalPlanksBlock;
import net.jmb19905.medievalworlds.common.block.SteelAnvil;
import net.jmb19905.medievalworlds.common.block.StoneAnvil;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
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

public class MWBlocks {

    public static List<BlockItem> blockItemOrder;
    private static final List<RegistryObject<BlockItem>> blockItemRegistryOrder = new ArrayList<>();

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MedievalWorlds.MOD_ID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MedievalWorlds.MOD_ID);

    public static final RegistryObject<RotatedPillarBlock> CHARCOAL_LOG = BLOCKS.register("charcoal_log", () -> new CharcoalLogBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> CHARCOAL_PLANKS = BLOCKS.register("charcoal_planks", () -> new CharcoalPlanksBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0f).sound(SoundType.WOOD)));

    public static final RegistryObject<StoneAnvil> STONE_ANVIL = BLOCKS.register("stone_anvil", StoneAnvil::new);
    public static final RegistryObject<Block> STEEL_ANVIL = BLOCKS.register("steel_anvil", SteelAnvil::new);

    public static final RegistryObject<Block> STEEL_BLOCK =
            BLOCKS.register("steel_block", () -> new Block(BlockBehaviour.Properties
                            .of(Material.METAL, MaterialColor.COLOR_GRAY)
                            .requiresCorrectToolForDrops()
                            .strength(5,6)
                            .sound(SoundType.METAL)
                    ));
    public static final RegistryObject<Block> SILVER_BLOCK =
            BLOCKS.register("silver_block", () -> new Block(BlockBehaviour.Properties
                            .of(Material.METAL, MaterialColor.QUARTZ)
                            .requiresCorrectToolForDrops()
                            .strength(5,3)
                            .sound(SoundType.METAL)
                    ));
    public static final RegistryObject<Block> BRONZE_BLOCK =
            BLOCKS.register("bronze_block", () -> new Block(BlockBehaviour.Properties
                            .of(Material.METAL, MaterialColor.COLOR_ORANGE)
                            .requiresCorrectToolForDrops()
                            .strength(3.5f,6)
                            .sound(SoundType.METAL)
                    ));
    public static final RegistryObject<Block> TIN_BLOCK =
            BLOCKS.register("tin_block", () -> new Block(BlockBehaviour.Properties
                            .of(Material.METAL, MaterialColor.COLOR_LIGHT_GRAY)
                            .requiresCorrectToolForDrops()
                            .strength(2.5f,6)
                            .sound(SoundType.METAL)
                    ));

    public static final RegistryObject<Block> SILVER_ORE =
            BLOCKS.register("silver_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties
                    .of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F, 3.0F)
            ));
    public static final RegistryObject<Block> TIN_ORE =
            BLOCKS.register("tin_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties
                    .of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F, 3.0F)
            ));

    public static final RegistryObject<Block> DEEPSLATE_SILVER_ORE =
            BLOCKS.register("deepslate_silver_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties
                    .copy(SILVER_ORE.get())
                    .color(MaterialColor.DEEPSLATE)
                    .strength(4.5F, 3.0F)
                    .sound(SoundType.DEEPSLATE)
            ));
    public static final RegistryObject<Block> DEEPSLATE_TIN_ORE =
            BLOCKS.register("deepslate_tin_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties
                    .copy(TIN_ORE.get())
                    .color(MaterialColor.DEEPSLATE)
                    .strength(4.5F, 3.0F)
                    .sound(SoundType.DEEPSLATE)
            ));

    public static final RegistryObject<Block> RAW_SILVER_BLOCK =
            BLOCKS.register("raw_silver_block", () -> new Block(BlockBehaviour.Properties
                    .of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY)
                    .requiresCorrectToolForDrops()
                    .strength(5,6)
            ));
    public static final RegistryObject<Block> RAW_TIN_BLOCK =
            BLOCKS.register("raw_tin_block", () -> new Block(BlockBehaviour.Properties
                    .of(Material.STONE, MaterialColor.COLOR_GRAY)
                    .requiresCorrectToolForDrops()
                    .strength(5,6)
            ));

    public static final RegistryObject<BlockItem> CHARCOAL_LOG_ITEM = registerBlockItem("charcoal_log", () -> new BlockItem(CHARCOAL_LOG.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> CHARCOAL_PLANKS_ITEM = registerBlockItem("charcoal_planks", () -> new BlockItem(CHARCOAL_PLANKS.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> STONE_ANVIL_ITEM = registerBlockItem("stone_anvil", () -> new BlockItem(STONE_ANVIL.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> STEEL_ANVIL_ITEM = registerBlockItem("steel_anvil", () -> new BlockItem(STEEL_ANVIL.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    public static final RegistryObject<BlockItem> STEEL_BLOCK_ITEM = registerBlockItem("steel_block", () -> new BlockItem(STEEL_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> SILVER_BLOCK_ITEM = registerBlockItem("silver_block", () -> new BlockItem(SILVER_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> BRONZE_BLOCK_ITEM = registerBlockItem("bronze_block", () -> new BlockItem(BRONZE_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> TIN_BLOCK_ITEM = registerBlockItem("tin_block", () -> new BlockItem(TIN_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    public static final RegistryObject<BlockItem> SILVER_ORE_ITEM = registerBlockItem("silver_ore", () -> new BlockItem(SILVER_ORE.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> TIN_ORE_ITEM = registerBlockItem("tin_ore", () -> new BlockItem(TIN_ORE.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    public static final RegistryObject<BlockItem> DEEPSLATE_SILVER_ORE_ITEM = registerBlockItem("deepslate_silver_ore", () -> new BlockItem(DEEPSLATE_SILVER_ORE.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> DEEPSLATE_TIN_ORE_ITEM = registerBlockItem("deepslate_tin_ore", () -> new BlockItem(DEEPSLATE_TIN_ORE.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

    public static final RegistryObject<BlockItem> RAW_SILVER_ITEM = registerBlockItem("raw_silver_block", () -> new BlockItem(RAW_SILVER_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> RAW_TIN_ITEM = registerBlockItem("raw_tin_block", () -> new BlockItem(RAW_TIN_BLOCK.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

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
