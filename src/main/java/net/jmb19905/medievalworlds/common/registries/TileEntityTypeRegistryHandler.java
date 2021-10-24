package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.tileentites.AlloyFurnaceTileEntity;
import net.jmb19905.medievalworlds.common.tileentites.BloomeryTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeRegistryHandler {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MedievalWorlds.MOD_ID);

    public static final RegistryObject<TileEntityType<AlloyFurnaceTileEntity>> ALLOY_FURNACE = TILE_ENTITY_TYPES.register("alloy_furnace", () -> TileEntityType.Builder.create(AlloyFurnaceTileEntity::new, BlockRegistryHandler.ALLOY_FURNACE_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<BloomeryTileEntity.Bottom>> BLOOMERY_BOTTOM = TILE_ENTITY_TYPES.register("bloomery_bottom", () -> TileEntityType.Builder.create(BloomeryTileEntity.Bottom::new, BlockRegistryHandler.BLOOMERY_BOTTOM_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<BloomeryTileEntity.Top>> BLOOMERY_TOP = TILE_ENTITY_TYPES.register("bloomery_top", () -> TileEntityType.Builder.create(BloomeryTileEntity.Top::new, BlockRegistryHandler.BLOOMERY_TOP_BLOCK.get()).build(null));

}
