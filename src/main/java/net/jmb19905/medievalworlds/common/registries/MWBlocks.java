package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.block.*;
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

public class MWBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MedievalWorlds.MOD_ID);

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

    public static final RegistryObject<Block> SIMPLE_BLOOMERY =
            BLOCKS.register("simple_bloomery", () -> new SimpleBloomery(BlockBehaviour.Properties
                    .of(Material.CLAY, MaterialColor.CLAY)
                    .sound(SoundType.GRAVEL)
                    .strength(0.6f)
                    .noOcclusion()
            ));

    public static final RegistryObject<Block> ALLOY_FURNACE = BLOCKS.register("alloy_furnace", () -> new AlloyFurnaceBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).strength(3.5f).lightLevel(value -> 13)));
    public static final RegistryObject<SlackTubBlock> SLACK_TUB_BLOCK = BLOCKS.register("slack_tub", () -> new SlackTubBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD)));



}
