package net.jmb19905.medievalworlds.core.registries;

import net.jmb19905.medievalworlds.common.block.anvil.CustomAnvilBlock;
import net.jmb19905.medievalworlds.common.block.grindstone.CustomGrindstoneBlock;
import net.jmb19905.medievalworlds.common.item.armor.CustomHorseArmorItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class VanillaReplacements {

    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    public static final RegistryObject<Block> ANVIL = BLOCKS.register("anvil", () -> new CustomAnvilBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.ANVIL)));
    public static final RegistryObject<Block> CHIPPED_ANVIL = BLOCKS.register("chipped_anvil", () -> new CustomAnvilBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.ANVIL)));
    public static final RegistryObject<Block> DAMAGED_ANVIL = BLOCKS.register("damaged_anvil", () -> new CustomAnvilBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.ANVIL)));

    public static final RegistryObject<Block> GRINDSTONE = BLOCKS.register("grindstone", () -> new CustomGrindstoneBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(2.0F, 6.0F).sound(SoundType.STONE)));

    public static final RegistryObject<BlockItem> ANVIL_ITEM = ITEMS.register("anvil", () -> new BlockItem(ANVIL.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> CHIPPED_ANVIL_ITEM = ITEMS.register("chipped_anvil", () -> new BlockItem(CHIPPED_ANVIL.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> DAMAGED_ANVIL_ITEM = ITEMS.register("damaged_anvil", () -> new BlockItem(DAMAGED_ANVIL.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final RegistryObject<BlockItem> GRINDSTONE_ITEM = ITEMS.register("grindstone", () -> new BlockItem(GRINDSTONE.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final RegistryObject<Item> GOLDEN_HORSE_ARMOR = ITEMS.register("golden_horse_armor", () -> new CustomHorseArmorItem(5, "gold", (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> IRON_HORSE_ARMOR = ITEMS.register("iron_horse_armor", () -> new CustomHorseArmorItem(7, "iron", (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_MISC)));

}