package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class MWFluids {

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MedievalWorlds.MOD_ID);

    public static final RegistryObject<FluidType> TEST_MOLTEN_METAL_TYPE = FLUID_TYPES.register("test", () -> new FluidType(FluidType.Properties
            .create()
            .descriptionId("block.medievalworlds.test")
            .canConvertToSource(false)
            .temperature(1300)
            .viscosity(6000)
            .density(3000)
            .pathType(BlockPathTypes.LAVA)
            .canDrown(false)
            .canSwim(false)
            .adjacentPathType(null)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
    ) {
        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
            consumer.accept(new IClientFluidTypeExtensions() {
                private static final ResourceLocation
                        STILL = new ResourceLocation(MedievalWorlds.MOD_ID, "block/test"),
                        FLOW = new ResourceLocation(MedievalWorlds.MOD_ID, "block/test");

                @Override
                public ResourceLocation getStillTexture()
                {
                    return STILL;
                }

                @Override
                public ResourceLocation getFlowingTexture()
                {
                    return FLOW;
                }
            });
        }
    });

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MedievalWorlds.MOD_ID);

    public static final RegistryObject<FlowingFluid> TEST_MOLTEN_METAL = RegistryObject.create(new ResourceLocation(MedievalWorlds.MOD_ID, "test"), ForgeRegistries.FLUIDS);
    public static final RegistryObject<FlowingFluid> FLOWING_TEST_MOLTEN_METAL = RegistryObject.create(new ResourceLocation(MedievalWorlds.MOD_ID, "flowing_test"), ForgeRegistries.FLUIDS);

    public static void registerFluids(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.FLUIDS, helper -> {
            ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(TEST_MOLTEN_METAL_TYPE, TEST_MOLTEN_METAL, FLOWING_TEST_MOLTEN_METAL).bucket(MWItems.TEST_MOLTEN_METAL_BUCKET).block(MWBlocks.TEST_LIQUID);

            helper.register(TEST_MOLTEN_METAL.getId(), new ForgeFlowingFluid.Source(properties));
            helper.register(FLOWING_TEST_MOLTEN_METAL.getId(), new ForgeFlowingFluid.Flowing(properties));
        });
    }

}
