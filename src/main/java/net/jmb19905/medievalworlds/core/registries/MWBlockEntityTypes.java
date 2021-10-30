package net.jmb19905.medievalworlds.core.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.blockentities.AlloyFurnaceBlockEntity;
import net.jmb19905.medievalworlds.common.blockentities.AnvilBlockEntity;
import net.jmb19905.medievalworlds.common.blockentities.bloomery.BloomeryBottomBlockEntity;
import net.jmb19905.medievalworlds.common.blockentities.bloomery.BloomeryTopBlockEntity;
import net.jmb19905.medievalworlds.common.blockentities.forge.ForgeBaseBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("ConstantConditions")
public class MWBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MedievalWorlds.MOD_ID);

    public static final RegistryObject<BlockEntityType<AlloyFurnaceBlockEntity>> ALLOY_FURNACE = BLOCK_ENTITIES.register("alloy_furnace", () -> BlockEntityType.Builder.of(AlloyFurnaceBlockEntity::new, MWBlocks.ALLOY_FURNACE_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<BloomeryBottomBlockEntity>> BLOOMERY_BOTTOM = BLOCK_ENTITIES.register("bloomery_bottom", () -> BlockEntityType.Builder.of(BloomeryBottomBlockEntity::new, MWBlocks.BLOOMERY_CLAY_BOTTOM_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<BloomeryTopBlockEntity>> BLOOMERY_TOP = BLOCK_ENTITIES.register("bloomery_top", () -> BlockEntityType.Builder.of(BloomeryTopBlockEntity::new, MWBlocks.BLOOMERY_CLAY_TOP_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<AnvilBlockEntity>> CUSTOM_ANVIL = BLOCK_ENTITIES.register("anvil", () -> BlockEntityType.Builder.of(AnvilBlockEntity::new, VanillaReplacements.ANVIL.get(), VanillaReplacements.CHIPPED_ANVIL.get(), VanillaReplacements.DAMAGED_ANVIL.get(), MWBlocks.STONE_ANVIL.get()).build(null));
    public static final RegistryObject<BlockEntityType<ForgeBaseBlockEntity>> FORGE_BASE = BLOCK_ENTITIES.register("forge_base", () -> BlockEntityType.Builder.of(ForgeBaseBlockEntity::new, MWBlocks.FORGE_BASE_BLOCK.get()).build(null));

}
