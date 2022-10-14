package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.block.CharcoalLogBlock;
import net.jmb19905.medievalworlds.common.block.CharcoalPlanksBlock;
import net.jmb19905.medievalworlds.common.block.SteelAnvil;
import net.jmb19905.medievalworlds.common.block.StoneAnvil;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
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

    public static final RegistryObject<BlockItem> CHARCOAL_LOG_ITEM = registerBlockItem("charcoal_log", () -> new BlockItem(CHARCOAL_LOG.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> CHARCOAL_PLANKS_ITEM = registerBlockItem("charcoal_planks", () -> new BlockItem(CHARCOAL_PLANKS.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> STONE_ANVIL_ITEM = registerBlockItem("stone_anvil", () -> new BlockItem(STONE_ANVIL.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));
    public static final RegistryObject<BlockItem> STEEL_ANVIL_ITEM = registerBlockItem("steel_anvil", () -> new BlockItem(STEEL_ANVIL.get(), new Item.Properties().tab(MedievalWorlds.blocksTab)));

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
