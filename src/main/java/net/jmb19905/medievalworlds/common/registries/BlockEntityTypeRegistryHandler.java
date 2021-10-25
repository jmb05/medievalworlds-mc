package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.blockentities.AlloyFurnaceBlockEntity;
import net.jmb19905.medievalworlds.common.blockentities.BloomeryBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("ConstantConditions")
public class BlockEntityTypeRegistryHandler {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MedievalWorlds.MOD_ID);

    public static final RegistryObject<BlockEntityType<AlloyFurnaceBlockEntity>> ALLOY_FURNACE = BLOCK_ENTITIES.register("alloy_furnace", () -> BlockEntityType.Builder.of(AlloyFurnaceBlockEntity::new, BlockRegistryHandler.ALLOY_FURNACE_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<BloomeryBlockEntity.Bottom>> BLOOMERY_BOTTOM = BLOCK_ENTITIES.register("bloomery_bottom", () -> BlockEntityType.Builder.of(BloomeryBlockEntity.Bottom::new, BlockRegistryHandler.BLOOMERY_BOTTOM_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<BloomeryBlockEntity.Top>> BLOOMERY_TOP = BLOCK_ENTITIES.register("bloomery_top", () -> BlockEntityType.Builder.of(BloomeryBlockEntity.Top::new, BlockRegistryHandler.BLOOMERY_TOP_BLOCK.get()).build(null));

}
