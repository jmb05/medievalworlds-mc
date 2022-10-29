package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.blockentities.AlloyFurnaceBlockEntity;
import net.jmb19905.medievalworlds.common.blockentities.AnvilBlockEntity;
import net.jmb19905.medievalworlds.common.blockentities.FletchingTableBlockEntity;
import net.jmb19905.medievalworlds.common.blockentities.SmithingTableBlockEntity;
import net.jmb19905.medievalworlds.common.blockentities.bloomery.SimpleBloomeryBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("ConstantConditions")
public class MWBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MedievalWorlds.MOD_ID);

    public static final RegistryObject<BlockEntityType<AnvilBlockEntity>> CUSTOM_ANVIL = BLOCK_ENTITIES.register("anvil", () -> BlockEntityType.Builder.of(AnvilBlockEntity::new, VanillaOverride.ANVIL.get(), VanillaOverride.CHIPPED_ANVIL.get(), VanillaOverride.DAMAGED_ANVIL.get(), MWBlocks.STONE_ANVIL.get(), MWBlocks.STEEL_ANVIL.get()).build(null));
    public static final RegistryObject<BlockEntityType<AlloyFurnaceBlockEntity>> ALLOY_FURNACE = BLOCK_ENTITIES.register("alloy_furnace", () -> BlockEntityType.Builder.of(AlloyFurnaceBlockEntity::new, MWBlocks.ALLOY_FURNACE.get()).build(null));
    public static final RegistryObject<BlockEntityType<SimpleBloomeryBlockEntity>> SIMPLE_BLOOMERY = BLOCK_ENTITIES.register("simple_bloomery", () -> BlockEntityType.Builder.of(SimpleBloomeryBlockEntity::new, MWBlocks.SIMPLE_BLOOMERY.get()).build(null));
    public static final RegistryObject<BlockEntityType<SmithingTableBlockEntity>> SMITHING_TABLE = BLOCK_ENTITIES.register("smithing_table", () -> BlockEntityType.Builder.of(SmithingTableBlockEntity::new, VanillaOverride.SMITHING_TABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<FletchingTableBlockEntity>> FLETCHING_TABLE = BLOCK_ENTITIES.register("fletching_table", () -> BlockEntityType.Builder.of(FletchingTableBlockEntity::new, VanillaOverride.FLETCHING_TABLE.get()).build(null));

}