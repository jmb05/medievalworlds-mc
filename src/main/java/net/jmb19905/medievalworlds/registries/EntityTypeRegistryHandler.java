package net.jmb19905.medievalworlds.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeRegistryHandler {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES, MedievalWorlds.MOD_ID);

    /*public static final RegistryObject<EntityType<DiamondSpearEntity>> IRON_SPEAR_ENTITY = ENTITY_TYPES.register("iron_spear_entity", () -> EntityType.Builder.<DiamondSpearEntity>create((type, worldIn) -> new IronSpearEntity(type, worldIn, "iron"), EntityClassification.MISC)
        .size(0.1f, 0.1f)
        .build(new ResourceLocation(MedievalWorlds.MOD_ID, "iron_spear_entity").toString()));
    public static final RegistryObject<EntityType<DiamondSpearEntity>> SILVER_SPEAR_ENTITY = ENTITY_TYPES.register("silver_spear_entity", () -> EntityType.Builder.<DiamondSpearEntity>create((type, worldIn) -> new SilverSpearEntity(type, worldIn, "iron"), EntityClassification.MISC)
            .size(0.1f, 0.1f)
            .build(new ResourceLocation(MedievalWorlds.MOD_ID, "silver_spear_entity").toString()));
    public static final RegistryObject<EntityType<DiamondSpearEntity>> STEEL_SPEAR_ENTITY = ENTITY_TYPES.register("steel_spear_entity", () -> EntityType.Builder.<DiamondSpearEntity>create((type, worldIn) -> new SteelSpearEntity(type, worldIn, "iron"), EntityClassification.MISC)
            .size(0.1f, 0.1f)
            .build(new ResourceLocation(MedievalWorlds.MOD_ID, "steel_spear_entity").toString()));
    public static final RegistryObject<EntityType<DiamondSpearEntity>> DIAMOND_SPEAR_ENTITY = ENTITY_TYPES.register("diamond_spear_entity", () -> EntityType.Builder.<DiamondSpearEntity>create(DiamondSpearEntity::new, EntityClassification.MISC)
            .size(0.1f, 0.1f)
            .build(new ResourceLocation(MedievalWorlds.MOD_ID, "diamond_spear_entity").toString()));
    */

}
