package net.jmb19905.medievalworlds.core.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.item.MetalMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryBuilder;

public class CustomRegistries {
    public static void init() {
        RegistryBuilder<MetalMaterial> registryBuilder = new RegistryBuilder<>();
        registryBuilder.setName(new ResourceLocation(MedievalWorlds.MOD_ID, "metal_material"));
        registryBuilder.setType(MetalMaterial.class);
        registryBuilder.create();
    }
}
