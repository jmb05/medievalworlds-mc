package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.containers.AlloyFurnaceContainer;
import net.jmb19905.medievalworlds.client.containers.BloomeryContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypeRegistryHandler {

    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, MedievalWorlds.MOD_ID);

    public static final RegistryObject<ContainerType<AlloyFurnaceContainer>> ALLOY_FURNACE = CONTAINER_TYPES.register("alloy_furnace", () -> IForgeContainerType.create(AlloyFurnaceContainer::new));
    public static final RegistryObject<ContainerType<BloomeryContainer>> BLOOMERY = CONTAINER_TYPES.register("bloomery", () -> IForgeContainerType.create(BloomeryContainer::new));

}
