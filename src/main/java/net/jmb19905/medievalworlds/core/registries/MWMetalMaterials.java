package net.jmb19905.medievalworlds.core.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.item.MetalMaterial;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class MWMetalMaterials {

    public static final DeferredRegister<MetalMaterial> METAL_MATERIALS = DeferredRegister.create(MetalMaterial.class, MedievalWorlds.MOD_ID);

    public static final RegistryObject<MetalMaterial> COPPER_MATERIAL = METAL_MATERIALS.register("copper", MetalMaterial::new);
    public static final RegistryObject<MetalMaterial> TIN_MATERIAL = METAL_MATERIALS.register("tin", MetalMaterial::new);
    public static final RegistryObject<MetalMaterial> BRONZE_MATERIAL = METAL_MATERIALS.register("bronze", MetalMaterial::new);
    public static final RegistryObject<MetalMaterial> IRON_MATERIAL = METAL_MATERIALS.register("iron", MetalMaterial::new);
    public static final RegistryObject<MetalMaterial> STEEL_MATERIAL = METAL_MATERIALS.register("steel", MetalMaterial::new);
    public static final RegistryObject<MetalMaterial> SILVER_MATERIAL = METAL_MATERIALS.register("silver", MetalMaterial::new);
    public static final RegistryObject<MetalMaterial> GOLD_MATERIAL = METAL_MATERIALS.register("gold", MetalMaterial::new);
    public static final RegistryObject<MetalMaterial> NETHERITE_MATERIAL = METAL_MATERIALS.register("netherite", MetalMaterial::new);

}
