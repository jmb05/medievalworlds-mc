package net.jmb19905.medievalworlds.core.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.blockentities.*;
import net.jmb19905.medievalworlds.common.blockentities.bellows.BellowsBlockEntity;
import net.jmb19905.medievalworlds.common.blockentities.bloomery.BloomeryBlockEntity;
import net.jmb19905.medievalworlds.common.blockentities.forge.ForgeBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("ConstantConditions")
public class MWBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MedievalWorlds.MOD_ID);

    public static final RegistryObject<BlockEntityType<AlloyFurnaceBlockEntity>> ALLOY_FURNACE = BLOCK_ENTITIES.register("alloy_furnace", () -> BlockEntityType.Builder.of(AlloyFurnaceBlockEntity::new, MWBlocks.ALLOY_FURNACE_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<BloomeryBlockEntity>> BLOOMERY = BLOCK_ENTITIES.register("bloomery", () -> BlockEntityType.Builder.of(BloomeryBlockEntity::new, MWBlocks.BLOOMERY.get()).build(null));
    public static final RegistryObject<BlockEntityType<BellowsBlockEntity>> BELLOWS = BLOCK_ENTITIES.register("bellows", () -> BlockEntityType.Builder.of(BellowsBlockEntity::new, MWBlocks.BELLOWS.get()).build(null));
    public static final RegistryObject<BlockEntityType<AnvilBlockEntity>> CUSTOM_ANVIL = BLOCK_ENTITIES.register("anvil", () -> BlockEntityType.Builder.of(AnvilBlockEntity::new, VanillaReplacements.ANVIL.get(), VanillaReplacements.CHIPPED_ANVIL.get(), VanillaReplacements.DAMAGED_ANVIL.get(), MWBlocks.STONE_ANVIL.get()).build(null));
    public static final RegistryObject<BlockEntityType<ForgeBlockEntity>> FORGE = BLOCK_ENTITIES.register("forge", () -> BlockEntityType.Builder.of(ForgeBlockEntity::new, MWBlocks.FORGE_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<QuernBlockEntity>> QUERN = BLOCK_ENTITIES.register("quern", () -> BlockEntityType.Builder.of(QuernBlockEntity::new, MWBlocks.QUERN.get()).build(null));
    public static final RegistryObject<BlockEntityType<SmithingTableBlockEntity>> SMITHING_TABLE = BLOCK_ENTITIES.register("smithing_table", () -> BlockEntityType.Builder.of(SmithingTableBlockEntity::new, VanillaReplacements.SMITHING_TABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<FletchingTableBlockEntity>> FLETCHING_TABLE = BLOCK_ENTITIES.register("fletching_table", () -> BlockEntityType.Builder.of(FletchingTableBlockEntity::new, VanillaReplacements.FLETCHING_TABLE.get()).build(null));

}
