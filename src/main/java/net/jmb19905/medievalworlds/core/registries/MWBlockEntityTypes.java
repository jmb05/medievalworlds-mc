package net.jmb19905.medievalworlds.core.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.blockentities.AnvilBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MWBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MedievalWorlds.MOD_ID);

    @SuppressWarnings("ConstantConditions")
    public static final RegistryObject<BlockEntityType<AnvilBlockEntity>> CUSTOM_ANVIL = BLOCK_ENTITIES.register("anvil", () -> BlockEntityType.Builder.of(AnvilBlockEntity::new, VanillaOverride.ANVIL.get(), VanillaOverride.CHIPPED_ANVIL.get(), VanillaOverride.DAMAGED_ANVIL.get(), MWBlocks.STONE_ANVIL.get()).build(null));

}
